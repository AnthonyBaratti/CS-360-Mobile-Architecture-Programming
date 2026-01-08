# Android Inventory Management App

A native Android Application built in **Java** that allows users to manage personal inventory items with full CRUD functionality. The app supports user authentication, local data persistence using **SQLite**, and a clean multi-screen UI built with standard Android components.<br><br>

## Features

- User registration and login (local authentication)
- User-specific inventory items
- Add, edit, and delete inventory items
- Inventory display using RecyclerView
- Local persistence using SQLite
- SMS permission managment screen
- Clean separation of UI, data, and model logic (controller)

## Application Flow
- Register or sign-in using local credentials
- View inventory items associated with logged-in user
- Add new inventory items with name, quantity, and description
- Edit existing inventory items
- Delete inventory items
- Manage SMS permissions from a dedicated screen

*NOTE Each user only sees and manages their own inventory data <br>

## Tech Stack
- Java
- Android (Native)
- SQLite
- Activities & XML Layouts
- RecyclerView
- Android Runtime Permissions

## Project Structure (High Level)
- Activies for each screen
- Adapter for RecyclerView inventory display
- Model classes representing inventory items
- SQLite database helper for data persistence
- XML layout files for UI Design

## Data Persistence
- Inventory and user data stored locally (SQLite)
- Data persists across app resets
- No external backend or connection required


## Security & Permissions
- Basic local authentication
- Inventory items scoped per user
- Explicit runtime permission handling SMS features
- No hard-coded credentials or API keys

## How to Run
- Clone the repository from GitHub
- Open project in Android Studio
- Sync Gradle Dependencies
- Run the app on an emulator (or physical android device)
