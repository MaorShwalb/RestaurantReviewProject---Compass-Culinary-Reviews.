## **RestaurantReviewProject.**
**An application written in JAVA language in Android Studio.
"RestaurantReviewApp",  your go-to platform for sharing and discovering the best culinary experiences!
My meticulously crafted application redefines the way you explore and critique restaurants, ensuring a seamless and enjoyable journey for every food aficionado.**
___

### **Table of Contents:**
- [Introduction](#RestaurantReviewProject)
- [Features](#features)
- [Usage](#Usage)
- [Installation](#installation)
- [The Architecture](#the-architecture)
- [Some Visualizations](#some-visualizations)
- [Technologies Used](#technologies-used)
- [Contact and Support](#contact-and-support)
___

### **Features.**
**1. online link is seamlessly integrated with a JSON-formatted database.**

**2. Google Firebase database which includes:**
- **authentication**, simplifies user authentication, offering secure and scalable authentication solutions for web and mobile applications.
- **Realtime Database** enables real-time synchronization of data across devices, providing a responsive and collaborative experience for users.
- **Firestore Database**, a NoSQL cloud database by Firebase, offers scalable and flexible data storage with real-time updates, optimizing performance for dynamic applications.

**Constructing the database:**
- Upon application launch, it automatically connects to the network and retrieves JSON data from a predefined web address. This data comprises the updated restaurant log. To prevent data duplication and ensure real-time updates, the application cross-references this list with the existing database in Firebase. Any new data, such as restaurants not present before, is seamlessly added to the database. This process guarantees that the database stays current without redundancies.
Subsequently, the application dynamically constructs an ordered list (of the RecyclerView type) on the "lobby" screen, presenting real-time information about the restaurants and their reviews in the aforementioned list.
___

### **Usage:**
#### Three distinct hierarchy levels are defined within the system:
>- **Viewer:**
Limited to viewing the list of restaurants and their associated reviews.
>- **Editor:**
Includes all "Viewer" capabilities.
Empowered to add reviews for the listed restaurants.
>- **ADMIN:**
Encompasses all "Editor" functionalities.
Has the authority to create additional "Editor" users.

#### Create users:
>The process of creating new users involves a "viewer" user requesting the opening of an account to become an "editor." This request is initiated after confirming adherence to regulations and sending an email to the "ADMIN." User creation is exclusively managed by the "ADMIN" user.
___

### **Installation:**

To run the Restaurant Menu app locally, follow these simple steps:

1. Clone the repository.
```
https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews.
```

2. Open the project in Android Studio.

3. Configure dependencies and SDK versions (if needed).

4. Build and run the application on your device or emulator.
___

### **The Architecture:**
![photo 1](https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/53367590-2b9c-41eb-9dc0-eda8c52b158c)
___

### **Some visualizations:** 
**Reminder: ADMIN contains an Editor that contains a Viewer**

(Explained in Usage)

| | |
| ------ | ----------- |
|**Registration&general screens**| <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/f4267abb-16e4-43a4-bc4b-c79bd6681435" alt="1" width="200"> <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/21d47f92-9c7c-4501-b438-4e66a8982bc5" alt="4" width="200"> <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/f110cafc-07e5-4304-b648-12f99f215cf4" alt="5" width="200"> |
|**Viewer screens** | <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/e01249eb-fabf-4102-97c9-d9e5912a1b6f" alt="view" width="200"> <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/64e5d680-a2cc-4895-b96b-35a9fed4d950" alt="view2" width="200"> |
|**Editor screens**| <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/5d8c570e-37ce-4735-ada1-0a668617870b" alt="user" width="200"> <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/d4056b44-c93f-4ad6-b9d7-24656caaf114" alt="admin-user" width="200"> <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/2d422f06-321a-43c9-bc0f-7b8605314358" alt="8" width="200"> |
|**ADMIN screens**| <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/d1c02e40-7d54-461b-ae94-cfcb22b91ba1" alt="admin" width="200"> <img src="https://github.com/MaorShwalb/RestaurantReviewProject---Compass-Culinary-Reviews./assets/97365304/69012923-6c23-48f9-abbb-91024b1853fa" alt="11" width="200"> |
___

### **Technologies Used.**
**JAVA:** owned by Oracle, widely-used, object-oriented programming language known for its platform independence and versatility in developing diverse applications.

**Android Studio:** The official integrated development environment (IDE) for Android development.

**Firebase:** comprehensive mobile and web development platform by Google, offering features such as real-time database, authentication, hosting, and cloud functions, simplifying backend development for developers.

<a href="https://www.oracle.com/il-en/java/" target="_blank"><img src="https://ichef.bbci.co.uk/news/976/cpsprodpb/139A5/production/_87339208_9c5c6008-16ae-4f26-bd98-a8538bf2b71b.jpg" alt="Firebase Logo" width="300"></a>
<a href="https://developer.android.com/studio" target="_blank"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStYvtnOKejWZZwRP9cSsvNgB3fpTQpVDytoA&usqp=CAU" alt="Firebase Logo" width="300"></a>
<a href="https://firebase.google.com" target="_blank"><img src="https://firebase.google.com/images/social.png" alt="Firebase Logo" width="300"></a>
___

### **Contact and Support.**

**If you have any questions about this project, need assistance, or have found any bugs or issues in the application, please don't hesitate to reach out. Your feedback is valuable and helps improve the quality of this project and my knowledge.
I appreciate your time and effort!
Thank you for your contribution and support!**




