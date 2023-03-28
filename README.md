## Full Project POO TV - :movie_camera:

### Explanations of the actions of stage II :clipboard:

**Update, Popup Notifications and Subscription Types** :newspaper:

All users see an array list of notifications & subscriptions according to certain genres of favorite movies or movies already owned.
Every time there are changes in the movie addition/deletion database, the user will be moved to the notification list. Everyone's notifications are kept even after the user disconnects, only operations to add notifications will be performed on the notification list.
When you subscribe to a certain genre of movies, it is added to the list of genres. And the list of genres is kept for each user even after disconnection.

***subscribe action UserUI package***: subscribing a user to a certain type of movie to which he is subscribed. The user subscribes to only one type of movies through this action. Keep each subscription each time the user exits and enters the platform. With each subscription to a certain type of movies, the subscriptions made are kept.

***recommendation action UserUI package***: premium users benefit from movie recommendations according to a top of the genres appreciated by users, number of likes given by users, in case of equality, the sorting is done by name genres. . Movies offered for recommendation have not been by premium user before.

***back action case in OutputActions***: the reverse of the change page action, instead of navigating forward with the pages according to the workflow, we do not return to the pages already visited, the action is executed for the last authenticated current user. We can return as long as we still have pages to visit or if we are not on the login/register pages that generate an error thus the return action.

***database in Database class***: add & delete operations on films in the list of existing films in the database. In the case of add & delete, notifications appear for users, different for these 2 new works, modify the list of user notifications & the list of films offered to subscribe by desired genres.

### Design Patterns project :key:

The Design Patterns that were used in the final realization of the project were thought in such a way that the final project would benefit from substantial improvements such as the number of lines, choices of variable numbers & objects, the elimination of repetitions of code sequences and errors caused by . overwriting or losing old object references.

In finding general, reusable solutions to problems that frequently appear in similar contexts. The designs were made in such a way as to be easy to find in the packages. Some of the packages bear the name of the design chosen for implementation, thus we have created separate packages that contain classes & interfaces that only implement the design with which they were predestined, now let's move on to the facts :smile::

* ***Singleton (Lazy Instantiation)*** :construction_worker: thus, the private constructor restricts the instantiation from other classes, but also because of the very frequent use of this class, therefore with Singleton I get rid of the many dependencies from each class from the project, which depends on certain classes in question, used for:
     * **UserCurrent** (*class*) - the current user authenticated in the account, during the project I had to have the current authenticated user at hand several times, being used in the vast majority of actions during the project, the assignment brings losses of values retained when we go from one method to another.
     * **InstanceAction** (*class*) - for most actions, here we wrote the file created **result_basic** for the execution of actions from package **in**. The write actions are the following, most of the methods were made as a way to reduce duplicate code, the list actions are for:
     1. Details about the list of movies, as well as for each movie taken individually.
     2. User details:
         * **credentials** - user account data
         * **movieList** - the list of movies that he can see, provided that the movie is not banned in his country
         * **rated**, **watched**, **liked**, **purchased** - the lists of films owned by the user, the lists are constantly changing, following the specific actions from Stage I
     3. Lists of movies purchased, watched, liked and rated by the user
     4. A new movie in the movie list with all existing movies from *see details*
     5. Details with all information about the authenticated user
     6. All notifications to each user
     7. All existing films in the user's country based on a sorting criterion
     8. All movies, including the new one, if it has been added to the database
     9. All registered information about the new user
     10. All movies starting with a given string startsWith
     11. All films not banned in a country
     12. Node with all the information about the movie, as well as for the user
     13. At the end of the basic *result_basic* the recommendations of the current premium user

* **Strategy** :chart_with_upwards_trend:, I thus select suitable algorithms with a certain context, for each type of movie selection action in the **showFilter** method at run-time. Thus, I no longer implement a single direct algorithm every time to solve my sorting, filtering & deleting movies, the code that takes care of listing the movies follows the filtering criteria from stage I.

* **Visitor** :oncoming_bus:, thus I create separate algorithms that operate a certain type of object. In the present case, the objects are provided by the **PurchaseAccount** & **PurchaseTokens** classes. The PurchaseAccount class uses the **buyPremium** method to buy a premium account or the user changes from STANDARD to PREMIUM. And the PurchaseTokens class uses the upgrade **buyTokens** by which they buy a certain number of requested tokens.

* **Factory** :factory:, thus allow the classes to postpone the instantiation of the objects that deal with the introduction, deletion & modification of the lists of bought, watched, rated and missed movies. I use an interface that contains the **actionMovie** method that will be implemented by a factory method that decides which object to create **FactoryMovie**. The constructors are as generic as possible, they are not polymorphic, thus allowing the subclasses **Like**, **Watch**, **Rate**, **Purchase** to be created, thus adding a polymorphic behavior to instantiation . Objects are created only when the action type is selected from the **see details** page.
