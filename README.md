# StudyNow

[![Android CI](https://img.shields.io/badge/Platform-Android-green)](https://developer.android.com/) 
[![Language](https://img.shields.io/badge/Language-Java-orange)](https://www.java.com/)


StudyNow is an Android application designed to help students manage their learning experience. The app features a modern and classy UI, secure login and registration system, and maintains user sessions for a seamless experience.

## Features

- **Splash Screen**: Animated GIF splash screen on app launch.
- **User Registration**: First-time users can register with full details:
  - Full Name
  - Email
  - Current Education (with selection list)
  - Year of Study
  - Username & Password
- **Login System**: Secure login with username and password using Room Database.
- **Session Management**: User session stored with SharedPreferences:
  - First-time user: Splash → Register → Login → MainActivity
  - Returning user: Splash → MainActivity
- **Classy UI**: Consistent color scheme and clean layouts for login, registration, and main screens.

## Screenshots

> Add screenshots here for Splash, Login, Register, and MainActivity.

## Technologies Used

- **Language**: Java
- **Database**: Room Database
- **Persistent Login**: SharedPreferences
- **UI**: ConstraintLayout, Material Components, LinearLayout
- **GIF Support**: `pl.droidsonroids.gif:android-gif-drawable`

## Installation

1. Clone the repository:

```bash
git clone https://github.com/mandalenikita368/StudyNow.git


StudyNow/
├── app/
│   ├── src/main/java/com/example/studynow/
│   │   ├── SplashActivity.java
│   │   ├── RegisterActivity.java
│   │   ├── LoginActivity.java
│   │   ├── MainActivity.java
│   │   └── database/
│   │       ├── AppDatabase.java
│   │       ├── User.java
│   │       └── UserDao.java
│   └── src/main/res/
│       ├── layout/
│       │   ├── activity_splash.xml
│       │   ├── activity_register.xml
│       │   ├── activity_login.xml
│       │   └── activity_main.xml
│       ├── values/
│       │   ├── strings.xml
│       │   └── colors.xml
│       └── mipmap/
│           ├── ic_launcher.png
│           └── ic_launcher_round.png
