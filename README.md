# Bank Android App

# Bank Payment Application

A modern Android payment application built with Kotlin and Jetpack Compose that handles both domestic and international money transfers through a single, reusable screen architecture.

## 🚀 Application Flow

### Welcome Screen
- 5-second delay simulating authentication and security checks
- Automatic navigation to payment selection after successful "login"

### Payment Selection Screen
- Choose between Domestic or International transfer
- Clean UI with centered buttons and app bar

### Payment Form Screen (Reusable Component)
- **Domestic Transfer**: Recipient Name, Account Number, Amount
- **International Transfer**: Additional IBAN and SWIFT code fields
- Real-time validation with immediate feedback
- Dynamic UI adaptation based on transfer type

### API Integration
- Successful payments navigate to Success Screen
- Errors displayed inline with detailed messages
- Loading states during transaction processing

### Success Screen
- Transaction confirmation display
- Option to return to payment selection
- Error handling for failed transactions

## 🛠️ Technical Stack
- **Kotlin** with Coroutines for asynchronous operations
- **Jetpack Compose** for modern declarative UI
- **MVVM Architecture** with Clean Architecture principles
- **Hilt** for dependency injection
- **Retrofit** for REST API communication
- **Navigation Component** for type-safe screen transitions
- **StateFlow** for reactive state management
- **Sealed Classes** for type-safe API responses

## 📋 Test Cases

### ✅ Successful Domestic Transfer
- Recipient Name: John Smith
- Account Number: 12345678
- Amount: 500.00
- Result: Transaction ID: TX1735834567890, Status: SUCCESS

### ✅ Successful International Transfer
-Recipient Name: Maria Garcia
-Account Number: 87654321
-Amount: 1000.00
-IBAN: GB29NWBK60161331926819
-SWIFT: ABCDUS33
-Result: Transaction ID: TX1735834567891, Status: SUCCESS

### ❌ Failed Domestic Transfer (Validation Error)
- Recipient Name: fail
- Account Number: 11111111
- Amount: 500.00
- Result: Error: Invalid account number

### ❌ Failed International Transfer (Insufficient Funds)
- Recipient Name: failed (test case)
- Account Number: 12348765
- Amount: 500.00
- IBAN: GB29NWBK60161331926819
-ed SWIFT: ABCDUS33