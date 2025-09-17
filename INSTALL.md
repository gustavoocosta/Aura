# ⚙️ Guia de Instalação - Music Streaming App

Este documento descreve como configurar e executar o projeto **Music Streaming App** localmente.

---

## 📌 Pré-requisitos

Certifique-se de ter instalado em sua máquina:

* [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
* **OU**, para execução manual:

  * [Java 17+](https://adoptium.net/)
  * [Maven](https://maven.apache.org/)
  * [Node.js 18+](https://nodejs.org/)
  * [PostgreSQL](https://www.postgresql.org/)

---

## ▶️ Executando com Docker Compose (recomendado)

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/music-streaming-app.git
cd music-streaming-app
```

2. Suba os serviços:

```bash
docker-compose up --build
```

3. Acesse:

* Backend: `http://localhost:8080`
* Frontend: `http://localhost:3000`
* Banco de dados PostgreSQL: `localhost:5432`

---

## ▶️ Executando Manualmente

### 1. Banco de Dados

* Crie um banco no PostgreSQL:

```sql
CREATE DATABASE musicdb;
```

* Execute o script `schema.sql` presente na raiz do projeto.

### 2. Backend

```bash
cd backend
mvn spring-boot:run
```

* API disponível em: `http://localhost:8080`

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

* App disponível em: `http://localhost:3000`

---

## 🧪 Testes

* Backend: utilize `mvn test`
* Frontend: utilize `npm test`

---

## 🚀 Deploy Futuro

Sugestões para produção:

* Deploy do backend em **AWS ECS ou Heroku**
* Banco de dados no **RDS (AWS)** ou **Cloud SQL (GCP)**
* Frontend em **Vercel ou Netlify**

---

📌 **Pronto!** Agora você já pode rodar o projeto localmente.
