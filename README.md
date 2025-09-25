# Online Order Booking (OMS) – Runbook

This repo contains a simple **Online Order Booking** system composed of 4 microservices:

- **order-service** – creates orders
- **payment-service** – authorizes/captures payments
- **inventory-service** – reserves/adjusts stock
- **notification-service** – sends order/payment notifications

Infra used in local/dev:
- **MySQL 8**
- **Kafka 3.7** (single broker)
- **Kafka UI** (optional)
- Docker / Docker Compose
- Kubernetes (Minikube on Windows)

---

## Prerequisites

- Docker Desktop (Windows) with the **Docker** engine running.
- **kubectl** and **minikube** installed (for the Kubernetes part).
- (Optional) A Docker Hub account if you want to **push** images and pull them in k8s.

> If you’re using **Minikube** with the Docker driver on Windows, be aware that `minikube service ... --url` runs a short-lived tunnel that requires the terminal to stay open.

---

## 1) Build & run locally with Docker Compose

From the repository root:

```powershell
docker compose -f docker-compose.yml -f docker-compose.services.yml up -d --build
```

This starts **MySQL**, **Kafka**, the **Kafka topics init**, and all **4 services**.

Check containers:
```powershell
docker ps
```

Stop everything:
```powershell
docker compose -f docker-compose.yml -f docker-compose.services.yml down
```

---

## 2) (Optional) Push images to Docker Hub

If you want Kubernetes to pull images from Docker Hub rather than loading them into Minikube, tag & push them to your registry. Below uses the account **`yogeshsolunke`** (change if needed).

```powershell
:: Tag
docker tag onlinebooking-notification-service:latest yogeshsolunke/onlinebooking-notification-service:latest
docker tag onlinebooking-inventory-service:latest    yogeshsolunke/onlinebooking-inventory-service:latest
docker tag onlinebooking-payment-service:latest      yogeshsolunke/onlinebooking-payment-service:latest
docker tag onlinebooking-order-service:latest        yogeshsolunke/onlinebooking-order-service:latest

:: Push (login first if required: docker login)
docker push yogeshsolunke/onlinebooking-notification-service:latest
docker push yogeshsolunke/onlinebooking-inventory-service:latest
docker push yogeshsolunke/onlinebooking-payment-service:latest
docker push yogeshsolunke/onlinebooking-order-service:latest
```

> **Alternative (no Docker Hub):** load local images straight into Minikube (see step 4B).

---

## 3) Kubernetes – apply manifests

> Ensure **Minikube** is running and your context is set:
>
> ```powershell
> minikube start --driver=docker --cpus=4 --memory=8192
> kubectl config use-context minikube
> kubectl get nodes
> ```

From the `kube-manifests` folder:

```powershell
kubectl apply -f namespace.yaml
kubectl -n oms apply -f mysql-secret.yaml -f mysql-initdb-configmap.yaml -f mysql-statefulset.yaml
kubectl -n oms apply -f kafka-deployment.yaml 
kubectl -n oms apply -f kafka-topics-job.yaml
kubectl -n oms apply -f order-deployment.yaml -f payment-deployment.yaml -f inventory-deployment.yaml -f notification-deployment.yaml

kubectl -n oms get pods -w
kubectl -n oms get all
```

### 3A) If you **pushed to Docker Hub**

Update deployments to pull those images (or edit YAML to point to Hub). Example using `kubectl set image`:

```powershell
kubectl -n oms set image deploy/order-service         app=yogeshsolunke/onlinebooking-order-service:latest
kubectl -n oms set image deploy/payment-service       app=yogeshsolunke/onlinebooking-payment-service:latest
kubectl -n oms set image deploy/inventory-service     app=yogeshsolunke/onlinebooking-inventory-service:latest
kubectl -n oms set image deploy/notification-service  app=yogeshsolunke/onlinebooking-notification-service:latest
```

> If your deployment images are tagged `:latest`, Kubernetes may **always pull**. Ensure your nodes have network access to Docker Hub, or set `imagePullPolicy: IfNotPresent` in the YAML.

### 3B) If you **did NOT push** (use local images)

Load local images into Minikube so the cluster can use them:

```powershell
minikube image load onlinebooking-order-service:latest
minikube image load onlinebooking-payment-service:latest
minikube image load onlinebooking-inventory-service:latest
minikube image load onlinebooking-notification-service:latest
```

Then make sure deployments use those images and do **not** always pull:

```powershell
kubectl -n oms set image deploy/order-service         app=onlinebooking-order-service:latest
kubectl -n oms set image deploy/payment-service       app=onlinebooking-payment-service:latest
kubectl -n oms set image deploy/inventory-service     app=onlinebooking-inventory-service:latest
kubectl -n oms set image deploy/notification-service  app=onlinebooking-notification-service:latest

# set imagePullPolicy = IfNotPresent (PowerShell-safe patch file method)
@'
{
  "spec": {
    "template": {
      "spec": {
        "containers": [
          { "name": "app", "imagePullPolicy": "IfNotPresent" }
        ]
      }
    }
  }
}
'@ | Set-Content -Encoding utf8 -NoNewline patch.json

kubectl -n oms patch deployment/order-service        --type merge --patch-file patch.json
kubectl -n oms patch deployment/payment-service      --type merge --patch-file patch.json
kubectl -n oms patch deployment/inventory-service    --type merge --patch-file patch.json
kubectl -n oms patch deployment/notification-service --type merge --patch-file patch.json

kubectl -n oms rollout restart deploy/order-service payment-service inventory-service notification-service
```

---

## 4) Access services from your machine

### Option A — Let Minikube print the URL
```powershell
minikube service -n oms order-service --url
minikube service -n oms payment-service --url
minikube service -n oms inventory-service --url
minikube service -n oms notification-service --url
```
> On Windows Docker driver you’ll see:
> `Because you are using a Docker driver on windows, the terminal needs to be open to run it.`  
> Keep the terminal open while you use the URL.

### Option B — NodePort via Minikube IP
```powershell
minikube ip
```
Use that IP with these ports (from `kubectl -n oms get svc`):
- order-service → **30088**
- payment-service → **30082**
- inventory-service → **30083**
- notification-service → **30084**

Example:
```powershell
curl http://<MINIKUBE_IP>:30088/actuator/health
```

### Option C — Port-forward (localhost)
```powershell
kubectl -n oms port-forward svc/order-service 8081:8081
# now open http://localhost:8081
```

---

## 5) Cleanup / Destroy resources

To remove everything you applied (keeping Docker images intact):

```powershell
# 1) Apps
kubectl -n oms delete -f order-deployment.yaml -f payment-deployment.yaml -f inventory-deployment.yaml -f notification-deployment.yaml --ignore-not-found

# 2) Kafka topics job
kubectl -n oms delete -f kafka-topics-job.yaml --ignore-not-found

# 3) Kafka
kubectl -n oms delete -f kafka-deployment.yaml --ignore-not-found

# 4) MySQL (StatefulSet + Service)
kubectl -n oms delete -f mysql-statefulset.yaml --ignore-not-found

# 5) MySQL config/secrets
kubectl -n oms delete -f mysql-initdb-configmap.yaml -f mysql-secret.yaml --ignore-not-found

# 6) Namespace (optional)
kubectl delete -f namespace.yaml --ignore-not-found
```

If you also want to stop the cluster:
```powershell
minikube stop
# or wipe completely (removes all cluster resources)
minikube delete
```

---

## Troubleshooting

### `kubectl` errors like “actively refused” / wrong localhost port
Your kube context points to an old API port. Refresh:
```powershell
minikube start --driver=docker
minikube update-context
kubectl config use-context minikube
kubectl cluster-info
```

### `ImagePullBackOff` / `ErrImagePull`
- Ensure images are **inside Minikube** (`minikube image load ...`) **or** your deployments point to a registry image: `kubectl set image ...`.
- Make sure `imagePullPolicy` is `IfNotPresent` if you rely on loaded images.
- Check which image the deploy uses:
  ```powershell
  kubectl -n oms get deploy order-service -o jsonpath="{.spec.template.spec.containers[0].image}`n"
  ```

### See logs
```powershell
kubectl -n oms logs deploy/order-service --tail=200
kubectl -n oms logs -f deploy/payment-service
kubectl -n oms logs job/init-topics
```

---

## Appendix – Handy one-liners

Restart all four services:
```powershell
kubectl -n oms rollout restart deploy/order-service payment-service inventory-service notification-service
```

Scale down/up all app deployments:
```powershell
kubectl -n oms scale deploy order-service payment-service inventory-service notification-service --replicas=0
kubectl -n oms scale deploy order-service payment-service inventory-service notification-service --replicas=1
```

Delete only app pods (they’ll be recreated):
```powershell
kubectl -n oms delete pod -l "app in (order-service,payment-service,inventory-service,notification-service)"
```

---

**That’s it!** You can copy/paste the blocks above as-is on Windows PowerShell.
