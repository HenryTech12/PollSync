# 🗳️ PollSync

Welcome to PollSync **Voting System**, a Spring Boot-powered web application that enables users to create, manage, and participate in polls.

## 🚀 Features
- User authentication & authorization
- Create and manage polls
- Vote on various polls
- View real-time poll results
- Fetch news using external APIs
- Responsive UI with HTML, CSS, and JavaScript
- Email Notifications
## 📂 Project Structure
```
VotingSystem/
├── src/
│   ├── main/
│   │   ├── java/com/votingapi/demo/
│   │   │   ├── controller/      # Handles HTTP requests
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # Database models
│   │   │   ├── repo/            # Repository layer
│   │   │   ├── service/         # Business logic and lots
│   │   ├── resources/
│   │   │   ├── static/          # Frontend assets
│   │   │   ├── templates/       # HTML templates
├── pom.xml                      # Maven dependencies
├── application.properties       # Configuration
```

## 🛠️ Installation & Setup
1. **Clone the repository**
   ```sh
   git clone https://github.com/yourusername/VotingSystem.git
   ```
2. **Navigate to the project directory**
   ```sh
   cd VotingSystem
   ```
3. **Build and run the project**
   ```sh
   mvn spring-boot:run
   ```
4. **Access the application**
   - Open `http://localhost:8080` in your browser
  

## API Key Setup

To use this project, you need to fetch an API key from [API Provider's Website](https://newsapi.org/v2/).  

1. Visit the website and sign up/log in.  
2. Navigate to the API section and generate a new API key.  
3. Copy the key and add it to your configuration file(application.properties:
   news-api-key = Generated API KEY
)

Example:  
news-api-key=your_api_key_here

#Make Sure To Setup Email App Password in application.properties file.

## 📸 Screenshots
![Voting Page](src/main/resources/static/assets/images/images1.jpg)
