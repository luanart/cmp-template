# AGENTS.MD: Project Intelligence & Contribution Guide

## 1. Project Overview
This is a **Compose Multiplatform (CMP)** template designed for Android and iOS development. It uses a modularized architecture with a heavy emphasis on **MVI (Model-View-Intent)** patterns and **Koin** for dependency injection.

### Key Technologies
* **Kotlin:** 2.3.0
* **Compose Multiplatform:** 1.10.0
* **Android Gradle Plugin (AGP):** 9.0.0
* **Dependency Injection:** Koin
* **Testing Stack:** Kotlin Test, Turbine (for Flow testing), and Mokkery.

## 2. Repository Structure
The project is split into several functional modules:
* **`androidApp`**: The Android-specific entry point and application class.
* **`iosApp`**: The Xcode project and Swift-based entry point for iOS.
* **`composeApp`**: The shared UI logic, including navigation and top-level Scaffolds.
* **`core/`**: Shared foundational logic:
    * `common`: Utility extensions (DateTime, Numbers) and platform-specific implementations.
    * `data`: Networking (Ktor), local storage, and repository implementations.
    * `presentation`: Base classes for MVI (`BaseViewModel`, `BaseScreen`) and reusable UI components.
* **`features/`**: Feature-specific modules (e.g., `auth`, `home`) containing screens, ViewModels, and navigation routes.
* **`buildLogic`**: Convention plugins for consistent Gradle configurations across modules.

## 3. Architecture & MVI Pattern
The repository follows a strict MVI pattern enforced by `BaseViewModel`.

### State Management
1.  **State**: Represents the UI data at any given time.
2.  **Intent**: User actions or system events (Action).
3.  **Effect**: One-time side effects like navigation or showing a snackbar.

### ViewModels
ViewModels extend `BaseViewModel<State, Action, Effect>`. They handle incoming actions and update the state or emit effects accordingly.

## 4. Testing Guide
Testing is centralized in `commonTest` to ensure cross-platform reliability.

### ViewModel Testing Patterns
When testing ViewModels, use the following pattern based on `BaseViewModelTest`:
* **Flow Validation**: Use **Turbine** to test `State` and `Effect` flows.
* **Coroutines**: Ensure you use `runTest` to handle asynchronous updates.
* **Mocks**: Use **Mokkery** to mock repository or service dependencies.

**Example Setup (Based on `BaseViewModelTest`):**
```kotlin
@Test
fun testStateChangeOnAction() = runTest {
    viewModel.state.test {
        // Initial state
        assertEquals(InitialState, awaitItem())
        
        // Trigger action
        viewModel.onAction(TestAction)
        
        // Assert new state
        assertEquals(NewState, awaitItem())
    }
}