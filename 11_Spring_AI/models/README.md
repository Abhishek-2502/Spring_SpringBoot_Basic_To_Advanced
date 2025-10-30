# Spring AI Tutorial with OpenAI, Anthropic and Ollama

This repository contains a Spring Boot application that demonstrates the integration of multiple Large Language Models (LLMs) including OpenAI, Anthropic, and Ollama, using the Spring AI library. The project also includes a React-based UI for comparing responses from different models.

## Project Structure

```
models/
├── src/
│   ├── main/
│   │   ├── java/              # backend code
│   │   ├── resources/         # Application properties and static resources
│   │   └── llm-comparison-ui/ # React frontend application
└── ...
```

## Features

- Integration with multiple LLM providers:
    - OpenAI (GPT models)
    - Anthropic (Claude models)
    - Ollama (Local models)
- REST API endpoints for interacting with each LLM
- React-based user interface for comparing model responses side-by-side
- Configurable prompts and model parameters

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Node.js and npm (for the React UI)
- API keys for OpenAI and Anthropic
- Ollama installed locally


### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Abhishek-2502/Spring_SpringBoot_Basic_To_Advanced.git
   cd Spring_SpringBoot_Basic_To_Advanced/SpringAIwithModels
  
   ```

2. Configure your API keys in `application.properties` or via environment variables:
   ```properties
   spring.ai.openai.api-key=your_openai_key
   spring.ai.anthropic.api-key=your_anthropic_key
   # Other configuration properties...
   ```

3. Build the Spring Boot application:
   ```bash
   mvn clean package -DskipTests
   ```

### Frontend Setup

1. Navigate to the React UI directory:
   ```bash
   cd src/main/llm-comparison-ui
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Build the UI:
   ```bash
   npm run dev
   ```


## Author

 - Abhishek Rajput

