# 📡 API Documentation - Music Streaming App

Base URL: `http://localhost:8080/api/music`

---

## 📌 Endpoints

### 1. Listar todas as músicas

**GET** `/api/music`

* **Descrição:** Retorna todas as músicas cadastradas.
* **Resposta 200 OK:**

```json
[
  {
    "id": 1,
    "title": "Imagine",
    "artist": "John Lennon",
    "album": "Imagine",
    "year": 1971
  },
  {
    "id": 2,
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "album": "A Night at the Opera",
    "year": 1975
  }
]
```

---

### 2. Buscar música por ID

**GET** `/api/music/{id}`

* **Descrição:** Retorna uma música específica pelo ID.
* **Resposta 200 OK:**

```json
{
  "id": 1,
  "title": "Imagine",
  "artist": "John Lennon",
  "album": "Imagine",
  "year": 1971
}
```

* **Resposta 404 Not Found:**

```json
{
  "error": "Music with ID 99 not found"
}
```

---

### 3. Criar nova música

**POST** `/api/music`

* **Descrição:** Cria um novo registro de música.
* **Body (JSON):**

```json
{
  "title": "Billie Jean",
  "artist": "Michael Jackson",
  "album": "Thriller",
  "year": 1982
}
```

* **Resposta 201 Created:**

```json
{
  "id": 3,
  "title": "Billie Jean",
  "artist": "Michael Jackson",
  "album": "Thriller",
  "year": 1982
}
```

* **Resposta 400 Bad Request (duplicado):**

```json
{
  "error": "Music already exists"
}
```

---

### 4. Atualizar música

**PUT** `/api/music/{id}`

* **Descrição:** Atualiza os dados de uma música existente.
* **Body (JSON):**

```json
{
  "title": "Imagine (Remastered)",
  "artist": "John Lennon",
  "album": "Imagine",
  "year": 2010
}
```

* **Resposta 200 OK:**

```json
{
  "id": 1,
  "title": "Imagine (Remastered)",
  "artist": "John Lennon",
  "album": "Imagine",
  "year": 2010
}
```

* **Resposta 404 Not Found:**

```json
{
  "error": "Music with ID 99 not found"
}
```

---

### 5. Deletar música

**DELETE** `/api/music/{id}`

* **Descrição:** Remove uma música do sistema.
* **Resposta 204 No Content:** sem body.
* **Resposta 404 Not Found:**

```json
{
  "error": "Music with ID 99 not found"
}
```

---

## ⚠️ Possíveis Erros

| Código | Motivo                                              |
| ------ | --------------------------------------------------- |
| 400    | Requisição inválida (campos faltando ou duplicados) |
| 404    | Música não encontrada                               |
| 500    | Erro interno do servidor                            |

---

📌 **Observação:** todos os endpoints retornam **JSON**.
