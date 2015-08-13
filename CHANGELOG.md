#Change Log

## Version 1.2

  * Added option to fix the header
    * Java *
    ```java
        autoHideHeaderLayout.setFixed(Boolean)
    ```

    * XML *
    ```xml
        app:fixed="true"
    ```


  * Added option to fix the header if the Body is smaller than the view
    * Java *
    ```java
        autoHideHeaderLayout.setFixedIfChildSmall(Boolean)
    ```

    * XML *
    ```xml
        app:fixedIfChildSmall="true"
    ```