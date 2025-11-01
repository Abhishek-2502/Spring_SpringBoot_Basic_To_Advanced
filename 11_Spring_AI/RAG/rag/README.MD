# RAGBot Spring AI

**RAGBot** is a Spring AI-powered Retrieval-Augmented Generation (RAG) application that allows users to upload documents and query information using vector search. It supports PostgreSQL with pgvector for vector storage and can use **Ollama** or **OpenAI** for embeddings and model queries.

---


## Table of Contents

* [Prerequisites](#prerequisites)
* [Architecture](#architecture)
* [Setup Instructions Local](#setup-instructions-local)
* [Setup Instructions Docker](#setup-instructions-docker)
* [Usage](#usage)
* [Screenshots](#screenshots)
* [Troubleshooting](#troubleshooting)
* [License](#license)
* [Author](#author)


## Prerequisites

1. **Docker and Docker Compose:** [Download Docker](https://www.docker.com/get-started)
    
    Ensure Docker is installed on your machine. 

2. **Ollama:** [Download Ollama](https://ollama.com/download)
    
    Ollama can be run locally or through Docker, but running Ollama from docker again and again is time consuming. So download it locally.  

---

## Architecture

  ![alt text](<screenshots/document_ingestion_pipeline.png>) 
  ![alt text](<screenshots/rag_architecture.png>) 

## Setup Instructions Local

### **1. Clone the Repository**
```bash
git clone https://github.com/Spring_SpringBoot_Basic_To_Advanced
cd Spring_SpringBoot_Basic_To_Advanced\11_Spring_AI\RAG\rag
```

### **2. Configure Spring Profiles**

- **Default Configuration (`application.properties`)**
  
- **Ollama-Specific Configuration (`application-ollama.properties`)**  

- **OpenAI-Specific Configuration (`application-openai.properties`)**  

### **3. Profile Activation**

Activate a profile by specifying it in the `application.properties` file or using the `SPRING_PROFILES_ACTIVE` environment variable.

#### **Activate Ollama**:
Set the following in `application.properties`:
```properties
spring.profiles.active=ollama
```

Or use an environment variable:
```bash
SPRING_PROFILES_ACTIVE=ollama ./mvnw spring-boot:run
```

#### **Activate OpenAI**:
Set the following in `application.properties`:
```properties
spring.profiles.active=openai
```

Or use an environment variable:
```bash
SPRING_PROFILES_ACTIVE=openai ./mvnw spring-boot:run
```

### **4. Pull Models for Ollama**

```bash
ollama pull nomic-embed-text
ollama pull phi3:mini
```

Verify the models:
```bash
ollama list
```

### **5. Start the Application**

Start the application with Maven:
```bash
./mvnw spring-boot:run
```

Access the application at:
```http://localhost:8081
```

---

## Setup Instructions Docker

### **1. Clone the Repository**
```bash
git clone https://github.com/Spring_SpringBoot_Basic_To_Advanced
cd Spring_SpringBoot_Basic_To_Advanced\11_Spring_AI\RAG\rag
```

### **2. Configure Spring Profiles**

Same as Above


### **3. Profile Activation**

Same as Above

### **4. Start the Application**

Start the application (Local Ollama):
```bash
docker-comopose -f compose-postgresql up --build -d 
```

Start the application (Ollama in Docker):
```bash
docker-comopose -f compose-ollama up --build -d 
```

Access the application at:
```http://localhost:8080
```

### **5. Pull Models for Ollama**

If you're using **Ollama** in Docker, pull the required models after starting the container:
```bash
docker exec -it ollama ollama pull nomic-embed-text 
docker exec -it ollama ollama pull phi3:mini
```

Verify the models:
```bash
docker exec -it ollama ollama list
```

### **6. Stopping the Services**

To stop and remove the containers:
```bash
docker-compose -f compose-ollama.yaml down
```

To stop and remove the containers if using Ollama locally:
```bash
docker-compose -f compose-postgresql.yaml down
```

---


## Usage

1. **Upload a Document**:
   Use the `/upload` endpoint to upload a PDF document. This will tokenize the content and store embeddings in the `documentgpt_vectors` table.

2. **Query Information**:
   Use the `/query` endpoint to ask questions based on the uploaded document.

---

## Screenshots

  ![alt text](<screenshots/upload.png>) 
  ![alt text](<screenshots/empty_chat.png>) 
  ![alt text](<screenshots/chat.png>) 

---

## Troubleshooting

### **Ollama-Specific Issues**
- **Error: Model Not Found**  
  Ensure you’ve pulled the required models:
  ```bash
  docker exec -it ollama ollama pull mistral
  ```

- **Verify Service**  
  Ensure the Ollama service is running:
  ```bash
  curl http://localhost:11434/models
  ```

### **OpenAI-Specific Issues**
- **API Key Missing**  
  Ensure your `spring.ai.openai.api-key` is correctly set in `application-openai.properties`.

- **Rate Limits**  
  OpenAI models may have rate limits. Monitor usage in your OpenAI dashboard.

---

## License

This project is licensed under the **MIT License** – see the [LICENSE](./LICENSE) file for details.

---

## Author
 
 - Abhishek Rajput
