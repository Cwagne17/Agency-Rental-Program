# Agency Rental Program

This application is a system that solves the problem Scenario given by my 237 Professor.
```
The agency offers various model cars, SUVs and trucks for rent to corporate customers (not private
individuals). Vehicles may be rented for a period of days, weeks, or months. Monthly rentals are cheaper
(on a daily basis) than weekly rentals, and weekly rentals are cheaper than daily rentals. There is also a
mileage charge and optional daily insurance. Rates differ for cars, SUVs and trucks. (There is no
difference in the rates among various model vehicles however.) Each corporate customer may choose
to be a “Prime Corporate Customer” for a yearly fee. Prime customers receive 100 free miles for each
individual rental. 
```

## How the Project is Organized

|Directories|   |
| -- | -- |
| `BackEnd` | Consists of Types that are used throughout the system (Accounts, Errors, Vehicles, Transactions, Rates) |
| `UI` | Consists of the different UIs as well as services that speaks to the back end |

## Overall Structure 
```
src
│   AgencyRentalProgram.java
│   README.md
│   
├───BackEnd
│   ├───Accounts
│   │       Account.java
│   │       AccountNode.java
│   │       Accounts.java
│   │       
│   ├───Errors
│   │       AccountNotFoundException.java
│   │       InvalidCreditCardException.java
│   │       ReservedVehicleException.java
│   │       UnreservedVehicleException.java
│   │       VINNotFoundException.java
│   │       
│   ├───Rates
│   │       CarRates.java
│   │       CurrentRates.java
│   │       SUVRates.java
│   │       TruckRates.java
│   │       VehicleRates.java
│   │       
│   ├───Transactions
│   │       Transaction.java
│   │       Transactions.java
│   │       
│   └───Vehicles
│           Car.java
│           Reservation.java
│           Suv.java
│           TimeSpan.java
│           Truck.java
│           Vehicle.java
│           VehicleNode.java
│           Vehicles.java
│           
└───UI
    │   EmployeeUI.java
    │   ManagerUI.java
    │   SystemInterface.java
    │   
    ├───Interfaces
    │       UserInterface.java
    │       
    └───Services
            RentalDetails.java
            ReservationDetails.java
```

