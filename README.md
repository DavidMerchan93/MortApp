# MortApp 🚀

A modern Android application built with Clean Architecture that displays Rick and Morty characters
using the [Rick and Morty API](https://rickandmortyapi.com/). The app features character browsing,
detailed views, and favorites management with offline support.

## 🏗️ Architecture

This project follows **Clean Architecture** principles with a multi-module structure:

```
📦 MortApp
├── 📱 app/                    # Main application module
├── 🎨 presentation/           # UI layer (Jetpack Compose)
├── 🏛️ domain/                 # Business logic and use cases
├── 🗄️ data/                   # Repository implementations
├── 💾 database/               # Local database (Room)
├── 🌐 network/                # Remote API (Ktor)
└── ⚙️ core/                   # Shared utilities and base classes
```

### Architecture Layers

- **Presentation Layer**: Jetpack Compose UI with MVVM pattern
- **Domain Layer**: Business logic, use cases, and entities
- **Data Layer**: Repository pattern with local and remote data sources
- **Database Layer**: Room database for offline storage
- **Network Layer**: Ktor HTTP client for API communication

## 🛠️ Tech Stack

### Core Technologies

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: Hilt
- **Asynchronous Programming**: Coroutines + Flow

### Data & Networking

- **Local Database**: Room
- **HTTP Client**: Ktor
- **Serialization**: Kotlinx Serialization

### Testing

- **Unit Testing**: JUnit 4, MockK
- **Coroutines Testing**: Kotlinx Coroutines Test
- **Flow Testing**: Turbine

### Code Quality & CI/CD

- **Static Analysis**: Detekt 1.23.7
- **Code Formatting**: KtLint 12.1.1
- **CI/CD**: GitHub Actions
- **Build System**: Gradle with Version Catalogs

### Navigation & State Management

- **Navigation**: Jetpack Navigation Compose
- **State Management**: StateFlow + Compose State

## 📱 Features

- ✅ **Character List**: Browse Rick and Morty characters with pagination
- ✅ **Character Details**: View detailed information about each character
- ✅ **Favorites Management**: Save and remove favorite characters
- ✅ **Offline Support**: Access previously viewed characters offline
- ✅ **Pull-to-Refresh**: Refresh character data
- ✅ **Modern UI**: Material Design with Jetpack Compose
- ✅ **Error Handling**: Comprehensive error states and retry mechanisms

## Media
<img width="380" height="1680" alt="Screenshot_1756141121" src="https://github.com/user-attachments/assets/d728142f-4bfc-44ef-b759-2074101508fb" />
<img width="380" height="1680" alt="Screenshot_1756141138" src="https://github.com/user-attachments/assets/41b7333f-2a78-4811-a9a4-2febee54d467" />
<img width="380" height="1680" alt="Screenshot_1756141144" src="https://github.com/user-attachments/assets/dfcea2cd-8c69-4da5-a4bb-359bbd9b4f82" />
<img width="380" height="1680" alt="Screenshot_1756141152" src="https://github.com/user-attachments/assets/f897a909-7e4a-43d2-9f68-d3001a9b40fe" />


## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17 or higher
- Android SDK 34
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/MortApp.git
   cd MortApp
   ```

2. **Open in Android Studio**
    - Launch Android Studio
    - Click "Open an existing project"
    - Navigate to the cloned directory and select it

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
    - Connect an Android device or start an emulator
    - Click the "Run" button in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

### Code Quality Commands

```bash
# Format code
./gradlew codeFormat

# Run quality checks
./gradlew codeQuality

# Run tests
./gradlew test

# Generate APK
./gradlew assembleDebug
```

## 🔄 CI/CD Pipeline

This project includes a comprehensive CI/CD pipeline using **GitHub Actions** with three workflows:

### 🔍 Main CI Pipeline (`.github/workflows/ci.yml`)

**Triggers**: Push to `main`/`develop`, PRs to these branches

**Steps**:

1. **Setup Environment**: JDK 17, Gradle caching
2. **Code Formatting Check**: `ktlintCheck` ensures consistent formatting
3. **Static Analysis**: `detekt` performs code quality analysis
4. **Unit Tests**: Runs all unit tests with coverage
5. **Artifact Upload**: Stores test reports and analysis results
6. **PR Comments**: Auto-comments on PRs with results

### 🏗️ Build Verification (`.github/workflows/build.yml`)

**Triggers**: Pull requests only

**Steps**:

1. **APK Generation**: Builds debug APK
2. **Artifact Upload**: Makes APK available for download
3. **Build Verification**: Ensures the app compiles successfully

### 🎯 Quality Gate (`.github/workflows/quality-gate.yml`)

**Triggers**: PRs to `main` branch

**Steps**:

1. **Comprehensive Analysis**: Full quality checks
2. **Detailed Reporting**: Generates quality metrics
3. **PR Integration**: Comments with quality insights
4. **Strict Validation**: Higher standards for main branch

### Quality Standards

- ✅ **Detekt**: Static code analysis with custom rules
- ✅ **KtLint**: Kotlin code formatting with Compose support
- ✅ **Unit Tests**: Automated test execution
- ✅ **Build Verification**: APK generation validation
- ✅ **Automated Reporting**: Quality metrics on each PR

## 📁 Project Structure

```
app/
├── src/main/java/com/davidmerchan/mortapp/
│   ├── di/                    # Dependency injection modules
│   ├── navigation/            # App navigation setup
│   └── MainActivity.kt        # Main activity

presentation/
├── src/main/java/com/davidmerchan/presentation/
│   ├── characters/            # Character list feature
│   ├── detail/               # Character detail feature
│   ├── favorites/            # Favorites feature
│   ├── mapper/               # Presentation mappers
│   └── utils/                # UI utilities

domain/
├── src/main/java/com/davidmerchan/domain/
│   ├── entities/             # Domain entities
│   ├── repository/           # Repository interfaces
│   └── useCase/              # Business use cases

data/
├── src/main/java/com/davidmerchan/data/
│   ├── repository/           # Repository implementations
│   └── mapper/               # Data mappers

database/
├── src/main/java/com/davidmerchan/database/
│   ├── dao/                  # Room DAOs
│   ├── entities/             # Database entities
│   └── di/                   # Database DI module

network/
├── src/main/java/com/davidmerchan/network/
│   ├── api/                  # API interfaces
│   ├── dto/                  # Network DTOs
│   └── di/                   # Network DI module
```

## 🧪 Testing

The project includes comprehensive testing:

- **Unit Tests**: Domain layer use cases and ViewModels
- **Repository Tests**: Data layer with mock implementations
- **Integration Tests**: End-to-end feature testing

Run tests:

```bash
# All tests
./gradlew test

# Specific module tests
./gradlew :domain:test
./gradlew :presentation:test
```

## 🔧 Configuration

### Environment Setup

- **Min SDK**: 24
- **Target SDK**: 34
- **Compile SDK**: 34
- **JVM Target**: 17

### API Configuration

The app uses the Rick and Morty API (no authentication required):

- Base URL: `https://rickandmortyapi.com/api/`
- Endpoints: Characters, locations, episodes

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

**Happy coding!** 🚀
