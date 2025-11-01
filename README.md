# CatbaSight Windows Application

A user-friendly desktop application for discovering and exploring tourist destinations in Catbalogan City, Philippines. Built with JavaFX, this application provides an intuitive interface for browsing landmarks, beaches, and resorts with detailed information, maps, and interactive features.

## 📸 Demo

![CatbaSight Demo](screenshots/demo.gif)

## 🌟 Features

- **Interactive Map View**: Navigate through destinations with visual map displays
- **Destination List**: Browse all available tourist spots with detailed information
- **Smart Sorting**: Sort destinations alphabetically or by user ratings
- **Destination Details**: View comprehensive information including:
  - Descriptions and history (for landmarks)
  - Owner contact information and pricing (for beaches/resorts)
  - Average user ratings
  - Distance from city hall
- **Google Maps Integration**: Get directions directly from the application
- **Rating System**: Rate destinations and provide feedback on your experience
- **Modern UI**: Clean and intuitive interface with icon support

## 🛠️ Technology Stack

- **Java 21** - Core programming language
- **JavaFX 20** - Modern UI framework
- **MySQL** - Database for storing destination data and ratings
- **Maven** - Dependency management and build tool
- **Ikonli** - Modern icon library for JavaFX

## 📋 Prerequisites

Before running this application, ensure you have:

- **JDK 21** or higher installed
- **Maven 3.6+** installed
- **MySQL Server** running with the `catbasight_db` database configured
- **JavaFX 20** runtime (included via Maven dependencies)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd CatbaSightNew
   ```

2. **Configure Database**
   - Ensure MySQL is running
   - Create the `catbasight_db` database
   - Set up the required tables:
     - `landmarks`
     - `beachesResorts`
     - `destinationratings`
     - `ratingexperience`
   - Update database connection settings in `DatabaseConnection.java` if needed

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn javafx:run
   ```
   
   Or run from your IDE by executing `Main.java`

## 📖 Usage

### Main Interface

- **MAP Button**: Switch to map view to see destination locations
- **LIST Button**: View all destinations in a scrollable list
- **ABOUT Button**: Learn more about the application

### Map View

- Use **←** and **→** buttons to navigate between destinations
- Click **Description** to view detailed information about a destination
- Click **Link to Get Directions** to open Google Maps directions

### List View

- **Double-click** any destination to view its description
- Use **Sort Alphabetically** to organize destinations by name
- Use **Sort by Rating** to see the highest-rated destinations first

### Rating System

- Select a destination and rating (1-5 stars) to submit your feedback
- Use the slider to rate your overall app experience

## 🗂️ Project Structure

```
CatbaSightNew/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/catbasightapp/
│       │       ├── Main.java                 # Application entry point
│       │       ├── MainInterfaceController.java
│       │       ├── MapDescriptionController.java
│       │       ├── MapDirectionsController.java
│       │       ├── DatabaseConnection.java
│       │       ├── Destination.java          # Base class
│       │       ├── Landmark.java            # Landmark implementation
│       │       ├── BeachesResort.java        # Beach/Resort implementation
│       │       └── SharedData.java           # Shared application data
│       └── resources/
│           └── com/example/catbasightapp/
│               ├── *.fxml                    # UI layouts
│               └── imagefiles/               # Application assets
├── pom.xml                                    # Maven configuration
└── README.md
```

## 🗄️ Database Schema

The application requires a MySQL database with the following tables:

- **landmarks**: Stores landmark destinations with history information
- **beachesResorts**: Stores beach/resort destinations with owner and pricing information
- **destinationratings**: Stores user ratings for destinations
- **ratingexperience**: Stores user experience ratings for the application

## 📝 Notes

- This application was developed as an academic project for Object-Oriented Programming (OOP) coursework
- The application is currently optimized for fixed window size (900x500)
- Database connection settings may need adjustment based on your MySQL configuration

## 👤 Author

**Reno-03**

---

## 📄 License

This project is developed for educational purposes. All rights reserved.

