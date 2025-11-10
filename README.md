# 🎯 Quizzify - Advanced Quiz Application

**Quizzify** is a smart, modern quiz platform that makes learning fun and competitive. Users can take quizzes, earn XP, and climb leaderboards, while admins manage content easily. Featuring OTP-based verification, secure JWT authentication, and elegant email workflows for a seamless learning experience.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache-red.svg)](https://redis.io/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

---

## ✨ Features

### 🔐 Authentication & Security
- **JWT-based authentication** with secure token management
- **OAuth2 Google Login** for seamless social authentication
- **OTP email verification** for new user registrations
- **Password reset** functionality with time-limited tokens
- **Role-based access control** (Admin, Creator, Player)
- **BCrypt password encryption** for enhanced security

### 📝 Quiz Management
- **Create and manage quizzes** with customizable settings
- **Multiple question types**: MCQ, Multi-select, True/False, Fill in the Blank
- **Three difficulty levels**: Easy, Medium, Hard
- **Dynamic marking system** with configurable multipliers
- **Negative marking support** for advanced assessments
- **Time-limited quizzes** with customizable duration
- **Tag-based organization** for easy categorization
- **Quiz analytics** including average scores and completion rates

### 🏆 Gamification & Leaderboards
- **Real-time leaderboards** powered by Redis sorted sets
- **XP/Points system** to reward user engagement
- **Global and quiz-specific rankings**
- **WebSocket integration** for live leaderboard updates
- **Achievement tracking** and progress monitoring

### 📊 Admin Dashboard
- **Comprehensive statistics** on users, quizzes, and attempts
- **Performance analytics** with visual chart data
- **User management** with role assignment and status control
- **Quiz moderation** and content management
- **System-level insights** including average completion times

### 📧 Email Notifications
- **Beautiful HTML email templates** for professional communication
- **OTP verification emails** with modern, responsive design
- **Password reset notifications** with secure action links
- **Gmail SMTP integration** with proper authentication

### 🔄 Real-time Features
- **WebSocket support** for live updates
- **Instant leaderboard synchronization**
- **Real-time attempt tracking**

---

## 🛠️ Tech Stack

### Backend Framework
- **Spring Boot 3.5.7** - Modern Java framework for enterprise applications
- **Spring Security** - Comprehensive security framework
- **Spring Data JPA** - Simplified database operations
- **Spring Data Redis** - Redis integration for caching and leaderboards
- **Spring WebSocket** - Real-time bidirectional communication
- **Spring Mail** - Email functionality with template support

### Database & Caching
- **PostgreSQL** - Robust relational database
- **Redis** - In-memory data structure store for caching and leaderboards

### Security & Authentication
- **JWT (JJWT 0.13.0)** - Secure token-based authentication
- **OAuth2 Client** - Google social login integration
- **BCrypt** - Password hashing algorithm

### API Documentation
- **Swagger/OpenAPI 3** (SpringDoc 2.7.0) - Interactive API documentation

### Additional Libraries
- **Lombok** - Reduces boilerplate code
- **ModelMapper** - Object mapping for DTOs
- **Dotenv** - Environment variable management
- **Thymeleaf** - Template engine for email templates

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK) 17 or higher**
- **Maven 3.9.x** or higher
- **PostgreSQL 12+** database server
- **Redis 6+** server
- **Git** for version control
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

---

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/quizzify.git
cd quizzify
```

### 2. Configure PostgreSQL Database

Create a new PostgreSQL database:

```sql
CREATE DATABASE quizzify_db;
CREATE USER quizzify_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE quizzify_db TO quizzify_user;
```

### 3. Set Up Redis

Ensure Redis is running on your local machine:

```bash
# For Linux/Mac
redis-server

# For Windows, use Redis for Windows or WSL
```

### 4. Configure Environment Variables

Create a `.env` file in the project root directory:

```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/quizzify_db
DB_USERNAME=quizzify_user
DB_PASSWORD=your_password

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# JWT Configuration
JWT_SECRET=your-256-bit-secret-key-minimum-32-characters-long
JWT_EXPIRATION=3600000

# Email Configuration (Gmail SMTP)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-specific-password

# Google OAuth2 Configuration
GOOGLE_CLIENT_ID=your-google-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your-google-client-secret

# Frontend Configuration
FRONTEND_URL=http://localhost:5173

# Server Configuration
SERVER_PORT=8080
```

### 5. Configure Google OAuth2

To enable Google login:

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the Google+ API
4. Create OAuth 2.0 credentials
5. Add authorized redirect URIs:
   - `http://localhost:8080/login/oauth2/code/google`
   - Your production URL when deploying
6. Copy the Client ID and Client Secret to your `.env` file

### 6. Configure Gmail SMTP (for email verification)

To use Gmail for sending emails:

1. Enable 2-factor authentication on your Google account
2. Generate an [App Password](https://myaccount.google.com/apppasswords)
3. Use this app password in the `MAIL_PASSWORD` environment variable

### 7. Build the Project

```bash
cd quiz-app-backend
mvn clean install
```

### 8. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

---

## 📚 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Docs**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### Key API Endpoints

#### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/verify-otp` - Verify email with OTP
- `POST /api/auth/login` - User login
- `POST /api/auth/forgot-password` - Initiate password reset
- `POST /api/auth/reset-password` - Reset password with token
- `GET /api/auth/me` - Get current user details

#### Quiz Management
- `POST /api/quizzes` - Create a new quiz
- `GET /api/quizzes` - Get all quizzes
- `GET /api/quizzes/{id}` - Get quiz by ID
- `PUT /api/quizzes/{id}` - Update quiz
- `DELETE /api/quizzes/{id}` - Delete quiz
- `GET /api/quizzes/tag/{tag}` - Get quizzes by tag

#### Question Management
- `POST /api/questions` - Create a new question
- `GET /api/questions/{id}` - Get question by ID
- `GET /api/questions/quiz/{quizId}` - Get all questions for a quiz
- `PUT /api/questions/{id}/update` - Update question marks/difficulty
- `DELETE /api/questions/{id}` - Delete question

#### Leaderboard
- `GET /api/leaderboard/{quizId}/top` - Get top N users
- `GET /api/leaderboard/{quizId}/user/{username}` - Get user rank
- `POST /api/leaderboard/{quizId}/update` - Update user score
- `DELETE /api/leaderboard/{quizId}/clear` - Clear leaderboard (Admin)

#### Admin Dashboard
- `GET /api/admin/dashboard/overview` - Dashboard statistics
- `GET /api/admin/dashboard/charts` - Chart data for analytics
- `GET /api/admin/users` - Manage users
- `GET /api/admin/quizzes` - Manage quizzes

---

## 📁 Project Structure

```
quiz-app-backend/
├── src/
│   ├── main/
│   │   ├── java/com/raj/quiz_app_backend/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── AppConfig.java
│   │   │   │   ├── RedisConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── SwaggerConfig.java
│   │   │   │   └── WebSocketConfig.java
│   │   │   │
│   │   │   ├── controller/          # REST API controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── QuizController.java
│   │   │   │   ├── QuestionController.java
│   │   │   │   ├── LeaderboardController.java
│   │   │   │   ├── admin/           # Admin-specific controllers
│   │   │   │   └── websockets/      # WebSocket controllers
│   │   │   │
│   │   │   ├── model/               # JPA entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Quiz.java
│   │   │   │   ├── Question.java
│   │   │   │   ├── Attempt.java
│   │   │   │   └── MarkingConfig.java
│   │   │   │
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── AuthRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── QuizRequest.java
│   │   │   │   └── QuizResponse.java
│   │   │   │
│   │   │   ├── repository/          # Spring Data JPA repositories
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── QuizRepository.java
│   │   │   │   ├── QuestionRepository.java
│   │   │   │   └── AttemptRepository.java
│   │   │   │
│   │   │   ├── security/            # Security & JWT components
│   │   │   │   ├── JwtService.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   │
│   │   │   ├── services/            # Business logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── QuizService.java
│   │   │   │   ├── QuestionService.java
│   │   │   │   ├── LeaderboardService.java
│   │   │   │   ├── EmailService.java
│   │   │   │   └── admin/           # Admin services
│   │   │   │
│   │   │   └── exception/           # Custom exceptions & handlers
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── ResourceNotFoundException.java
│   │   │       └── BadRequestException.java
│   │   │
│   │   └── resources/
│   │       ├── application.yaml     # Application configuration
│   │       └── templates/           # Email templates
│   │           ├── email_otp_template.html
│   │           └── password_reset_template.html
│   │
│   └── test/                        # Unit and integration tests
│
├── .env                             # Environment variables (not in repo)
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

---

## 🔧 Configuration Details

### JWT Configuration

The application uses JWT tokens for stateless authentication. Configure the following in your `.env`:

- `JWT_SECRET`: A strong secret key (minimum 32 characters for HS256)
- `JWT_EXPIRATION`: Token expiration time in milliseconds (default: 1 hour)

### Email Templates

The application includes beautiful, responsive HTML email templates located in `src/main/resources/templates/`:

- **email_otp_template.html**: Used for email verification with OTP
- **password_reset_template.html**: Used for password reset requests

Templates use placeholder syntax `{{VARIABLE}}` that gets replaced with actual values.

### Redis Leaderboard Structure

Leaderboards are stored in Redis using sorted sets with the key pattern:
```
leaderboard:{quizId}
```

Each entry contains:
- Member: username
- Score: user's score in that quiz

---

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

Run with coverage:

```bash
mvn clean test jacoco:report
```

---

## 🚢 Deployment

### Production Checklist

Before deploying to production, ensure you:

1. Change `spring.jpa.hibernate.ddl-auto` to `validate` or `none`
2. Use strong, unique secrets for JWT and database passwords
3. Enable HTTPS for secure communication
4. Configure proper CORS settings for your frontend domain
5. Set up proper logging and monitoring
6. Use environment-specific configuration files
7. Enable Redis password authentication
8. Configure proper backup strategies for PostgreSQL

### Docker Deployment (Optional)

Create a `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/quiz-app-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
docker build -t quizzify-backend .
docker run -p 8080:8080 --env-file .env quizzify-backend
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

This project follows standard Java conventions:
- Use meaningful variable and method names
- Add appropriate comments for complex logic
- Write unit tests for new features
- Keep methods focused and concise
- Follow SOLID principles

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Authors

**Your Name** - [GitHub Profile](https://github.com/Rajmathur-8)

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Redis for high-performance caching and leaderboards
- PostgreSQL for robust data persistence
- All contributors who helped improve this project

---

## 📧 Contact & Support

For questions, issues, or suggestions:

- **Email**: support@quizzify.com
- **GitHub Issues**: [Create an issue](https://github.com/Rajmathur-8/Quizzify/issues)
- **Documentation**: [Wiki](https://github.com/yourusername/quizzify/wiki)

---


---

**Made with ❤️ by the Quizzify Team**
