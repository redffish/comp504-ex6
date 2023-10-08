https://jh166-ex6-ball-wall-b85b3b27cf7f.herokuapp.com/  

Fix: When calling the update method on all the paint object observers, need to use `getPropertyChangeListeners(String propertyName)` instead of `getPropertyChangeListeners()`.  
See docs: https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/beans/PropertyChangeSupport.html  
