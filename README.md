🔎 Campus Lost & Found

An Android app that helps university students recover lost belongings (calculators, keys, ID cards, phones and more). Students post items they find, with the exact location and time, and owners can contact the finder directly.

Built with Java + XML and Material Design 3.

✨ Features
🔐 Sign up & login with name, phone, email and password
📝 Post found items with category, title, description and landmark
📍 Auto location: asks for location permission and fills in the current GPS position
🕒 Found time: defaults to now, editable with a time picker
📋 Feed of items posted by other students, with search and category filters
📞 Contact the finder by call or SMS
🗺️ View on map: opens the item's location in a maps app
👤 Profile with your details and post count
🎨 Modern, clean UI with a floating bottom navigation bar
🛠️ Tech Stack
Area	Details
Language	Java
UI	XML layouts, Material Design 3 (Material Components 1.12)
Storage	SharedPreferences (JSON)
Location	Android LocationManager
Min SDK	26 (Android 8.0)
Target SDK	34
📁 Project Structure
app/src/main/
├── java/com/campus/lostfound/
│   ├── AuthActivity.java       # Login / Sign up
│   ├── MainActivity.java       # Bottom navigation host
│   ├── FeedFragment.java       # Items list, search, filters
│   ├── PostFragment.java       # Post item + location
│   ├── ProfileFragment.java    # User profile, logout
│   └── Db.java                 # SharedPreferences helper
└── res/                        # Layouts, drawables, themes, menu
🚀 Getting Started
Clone the repository
bash
   git clone https://github.com/EPIC-ISMAEEL/AppNova-TeamIsmail.git
Open the project in Android Studio
Let Gradle sync finish
Run on an emulator or a real device (a real device is best for testing GPS)
📸 Screenshots

Add your screenshots here.

Login	Feed	Post	Profile
			
⚠️ Limitations
Data is stored locally with SharedPreferences, so posts are not shared between different phones.
Passwords are stored in plain text, which is fine for a demo but not for production.
🔮 Future Improvements
Cloud backend (Firebase) so all students see the same posts
Photo upload for found items
In-app chat and push notifications
"Mark as returned" status
Password hashing and email verification
👨‍💻 Author

Ismaeel Ejaz GitHub: @EPIC-ISMAEEL
