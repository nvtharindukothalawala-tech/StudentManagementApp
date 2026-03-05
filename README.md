# 🎓 Student Management App

An Android mobile application for managing student records using **SQLite** as the local database. Built with **Java** in **Android Studio**.

---

## 📱 App Screenshot

<p align="center">
  <img src="screenshot/app_screenshot.jpeg" width="280" alt="Student Management App Screenshot"/>
</p>

---

## 📋 About The Project

This application was developed as part of a **Mobile Application Development** activity focused on connecting a SQLite database to an Android application. It demonstrates full **CRUD (Create, Read, Update, Delete)** operations on a local SQLite database.

### ✅ Features
- ➕ **Add** new student records (Name, Email, Phone)
- 📋 **View** all students in a RecyclerView list
- ✏️ **Edit/Update** existing student records
- 🗑️ **Delete** student records with confirmation dialog
- 🔢 **Live counter** showing total number of students
- ✔️ **Form validation** (empty fields, invalid email format)
- 💾 **Persistent storage** using SQLite database

---

## 🗄️ Database Structure

**Database Name:** `StudentDB.db`

**Table Name:** `students`

| Column | Data Type | Constraint |
|--------|-----------|------------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT |
| name | TEXT | NOT NULL |
| email | TEXT | UNIQUE NOT NULL |
| phone | TEXT | NOT NULL |

---

## 🔧 How the Database Connection Works

SQLite is built directly into Android — no external server or library needed.

```
App starts
    ↓
DatabaseHelper(context) called
    ↓
Android checks: Does StudentDB.db exist?
    ├── NO  → onCreate() → CREATE TABLE students
    └── YES → Open existing database
    ↓
SQLiteDatabase object returned
    ↓
CRUD operations executed
    ↓
db.close() — releases file lock
```

**Database file location on device:**
```
/data/data/com.example.studentmanagement/databases/StudentDB.db
```

---

## 💻 CRUD Methods

```java
// INSERT
long insertStudent(String name, String email, String phone)

// SELECT ALL
List<Student> getAllStudents()

// UPDATE
int updateStudent(int id, String name, String email, String phone)

// DELETE
int deleteStudent(int id)
```

---

## 📁 Project Structure

```
StudentApp/
├── app/src/main/
│   ├── AndroidManifest.xml
│   ├── java/com/example/studentmanagement/
│   │   ├── DatabaseHelper.java     ← SQLiteOpenHelper + CRUD
│   │   ├── Student.java            ← Data model class
│   │   ├── StudentAdapter.java     ← RecyclerView adapter
│   │   └── MainActivity.java      ← UI + button logic
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml   ← Main screen layout
│       │   └── item_student.xml    ← Student row layout
│       ├── drawable/
│       │   ├── edittext_background.xml
│       │   ├── id_badge_background.xml
│       │   ├── btn_edit_background.xml
│       │   └── btn_delete_background.xml
│       └── values/
│           ├── themes.xml
│           └── strings.xml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## 🚀 How To Run This Project

### Prerequisites
- Android Studio (latest version)
- Android device or emulator (API 21+)
- Java Development Kit (JDK 8+)

### Steps

**1. Clone the repository:**
```bash
git clone https://github.com/nvtharindukothalawala-tech/StudentManagementApp.git
```

**2. Open in Android Studio:**
- Open Android Studio
- Click `File → Open`
- Select the `StudentApp` folder
- Wait for Gradle sync to complete

**3. Add gradle.properties fix:**

Make sure `gradle.properties` contains:
```
android.useAndroidX=true
android.enableJetifier=true
```

**4. Run the app:**
- Connect your Android device via USB with USB Debugging enabled
- Click the green ▶ Run button
- Select your device and click OK

---

## 📦 Dependencies

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
}
```

---

## 📱 Tested On

| Device | Android Version | Status |
|--------|----------------|--------|
| Samsung SM-A127F | Android 13.0 (Tiramisu) | ✅ Working |

---

## 🛠️ Built With

- **Language:** Java
- **IDE:** Android Studio
- **Database:** SQLite (android.database.sqlite)
- **UI Components:** RecyclerView, CardView, Material Design
- **Min SDK:** API 21 (Android 5.0)
- **Target SDK:** API 34 (Android 14)

---

## 👨‍💻 Author

**Tharindu Kothalawala**
- GitHub: [@nvtharindukothalawala-tech](https://github.com/nvtharindukothalawala-tech)

---

## 📄 License

This project is open source and available for educational purposes.
