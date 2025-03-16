# **ARLearner** 🎓📚

### **An interactive Android app that uses Augmented Reality (AR) to visualize 3D objects for learning alphabets and play a quiz based on object recognition.**

---

## **🚀 Features**
✔ **AR Alphabet Learning** – Select an alphabet and place the corresponding 3D object in an AR scene.<br>
✔ **Quiz Mode** – Identify the correct starting alphabet for a randomly displayed 3D object.<br>
✔ **Jetpack Compose UI** – A modern and interactive UI.<br>
✔ **SceneView AR** – Integrated AR using `ARSceneView`.<br>
✔ **Gesture Controls** – Tap on a surface to place 3D objects dynamically.<br>
✔ **Optimized Object Management** – Ensures smooth AR interactions and memory management.<br>

---

## **📸 Screenshots**
<p align="center">
  <img src="Screenshot_1.png" width="200"> 
  <img src="Screenshot_2.png" width="200"> 
</p>

---

## **🛠 Tech Stack**
- **Kotlin** – Primary language<br>
- **Jetpack Compose** – UI framework<br>
- **SceneView AR** – AR rendering and interactions<br>
- **Navigation Component** – App navigation management<br>
- **MutableState & State Management** – For handling UI updates<br>
- **Material Design 3** – For UI components & themes<br>
- **Sketchfab** – Source for 3D `.glb` models<br>

---

## **📦 Installation**

1️⃣ **Clone the repository**<br>
```bash
git clone https://github.com/your-username/ARLearner.git
```
2️⃣ **Open in Android Studio**<br>
3️⃣ **Build & Run on an AR-supported device**<br>

---

## **🔧 Dependencies**
Ensure you have the following dependencies in your `build.gradle`:
```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
implementation("androidx.navigation:navigation-compose:2.7.7")
implementation("io.github.sceneview:arsceneview:2.2.1")
implementation("io.github.sceneview:sceneview:2.0.0")
```

---

## **📄 App Structure & Implementation**
### **1️⃣ Alphabet Selection Screen**
📌 Displays a **grid of alphabets**. Selecting an alphabet navigates to the AR scene.<br>
📌 Uses **Jetpack Compose** for the UI.<br>

### **2️⃣ AR Scene (ARScreen.kt)**
📌 Initializes the **AR environment** using `rememberEngine()`.<br>
📌 Loads the 3D models dynamically based on selected alphabet.<br>
📌 **Tap gesture** to place models using `onSingleTapConfirmed`.<br>
📌 **Removes models & clears memory** on exit.<br>

### **3️⃣ Model Management (Utils.kt)**
📌 **Loads `.glb` models dynamically** from the `models/` folder.<br>
📌 **Creates anchor nodes** to attach objects to the AR plane.<br>
📌 **Handles memory cleanup** when objects are removed.<br>


## **📄 Ensure Camera & AR Permissions**
📌 Add this to your `AndroidManifest.xml`:<br>
```xml
<uses-permission android:name="android.permission.CAMERA"/>
    <uses-feature android:name="android.hardware.camera.ar"/>
    <uses-feature android:glEsVersion="0x00020000" android:required="true"/>
    <meta-data
        android:name="com.google.ar.core"
        android:value="required" />
```

---

## **🎯 Usage**
1️⃣ **Select an Alphabet** – Choose an alphabet from the selection screen.<br>
2️⃣ **AR View** – Place 3D objects in real-world space using tap gestures.<br>
3️⃣ **Quiz Mode** – Identify the correct alphabet for a randomly displayed object.<br>

---

<h2 align="center">⭐ Like this project? Give it a star! 🌟</h2>
<h3 align="center">If you found this project helpful, consider giving it a ⭐ on GitHub! 🚀</h3>

# ARLearner
