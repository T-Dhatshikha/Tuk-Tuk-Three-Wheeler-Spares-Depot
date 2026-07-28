# Auto Spare Parts Management System (SpareDepot)

A desktop application designed to manage spare parts inventory, process point-of-sale transactions, track dealer information, and maintain audit logs.

---

## Technical Specifications

* **JDK Version:** Java Development Kit 26 (`26.0.1`)
* **JavaFX Version:** JavaFX SDK 26 (`26.0.2`)
* **Testing Framework:** JUnit 4 (`4.13.2`)
* **IDE Used:** IntelliJ IDEA 2026.1

---

## Project Structure Overview

* `src/`: Contains all backend models, logic managers, service handlers, and JavaFX controller classes.
* `fxml/`: Contains FXML layout templates defining the user interface screens.
* `images/`: Stores visual assets and product photos used across the application.
* Data Files (`.txt`): Plaintext data stores holding inventory records and supplier listings.
* Test Suite : Unit tests validating backend business rules and sorting functionality.

---

## Instructions to Run the Application

### Running via IntelliJ IDEA
1. Open the project folder in IntelliJ IDEA.
2. Confirm that JDK 26 is set as the Project SDK under **File -> Project Structure -> Project**.
3. Verify that the JavaFX SDK library modules (`C:\javafx-sdk-26\lib`) are added under **File -> Project Structure -> Libraries**.
4. Open `src/Main.java`.
5. Run the application by clicking the green **Run** button or pressing `Shift + F10`.

### Key Assumptions

* Each spare code is strictly unique; duplicates are not allowed.
* The "Date Added" field is stored as a plain string with no specific formatting or validation applied.
* The default low-stock threshold is fixed at 10.
* Users can input custom values to update individual spare part thresholds.
* Users can dynamically adjust the viewing threshold to filter low-stock items.
* At least 4 valid dealers must exist in the dealers file for the random selection feature to work properly.
* Bulk discounts apply per individual cart item based on its quantity, not the total cart item count.
* The synergy discount applies to the remaining full cart subtotal after all individual bulk discounts have been applied.

GitHub Repository : (https://github.com/T-Dhatshikha/Tuk-Tuk-Three-Wheeler-Spares-Depot.git)

