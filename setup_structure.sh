# Navigate to your desired parent directory
mkdir -p src/main/java/com/shivam/LoginApp/{controller,dto,model,repository,security,service}
mkdir -p src/main/resources/static/{css,js}
mkdir -p src/main/resources/templates
mkdir -p src/test/java/com/shivam/LoginApp/{controller,repository,security,service}

# Main application file
touch src/main/java/com/shivam/LoginApp/LoginAppApplication.java

# Controller
touch src/main/java/com/shivam/LoginApp/controller/AuthController.java

# DTOs
touch src/main/java/com/shivam/LoginApp/dto/{JwtResponse.java,LoginRequest.java,RegisterRequest.java}

# Model
touch src/main/java/com/shivam/LoginApp/model/User.java

# Repository
touch src/main/java/com/shivam/LoginApp/repository/UserRepository.java

# Security
touch src/main/java/com/shivam/LoginApp/security/{JwtAuthenticationEntryPoint.java,JwtAuthenticationFilter.java,JwtConfig.java,JwtUtils.java,UserDetailsServiceImpl.java,WebSecurityConfig.java}

# Service
touch src/main/java/com/shivam/LoginApp/service/UserService.java

# Resources
touch src/main/resources/application.properties
touch src/main/resources/static/css/styles.css
touch src/main/resources/static/js/login.js
touch src/main/resources/static/index.html

# Test files
touch src/test/java/com/shivam/LoginApp/controller/AuthControllerTest.java
touch src/test/java/com/shivam/LoginApp/repository/UserRepositoryTest.java
touch src/test/java/com/shivam/LoginApp/security/{JwtUtilsTest.java,UserDetailsServiceImplTest.java}
touch src/test/java/com/shivam/LoginApp/service/UserServiceTest.java
