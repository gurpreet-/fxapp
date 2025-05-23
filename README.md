
<img src="https://github.com/gurpreet-/fxapp/assets/6073488/471ea20b-008f-4970-9e3b-565d3b9fc367" width="190" alt="logo"> 

A currency conversion app written in Jetpack Compose using an MVI+CleanArch architecture. Currently this app supports `EUR`, `GBP` and `USD` and shows a list of currencies that any amount you provide gets converted to.

**Note**: The API refreshes rates around 16:00 CET every **working** day.


#### Performance
- The app is using a reactive view system known as Jetpack Compose which only updates when a `Flow` updates its data. 
- I've tried to use as little dependencies as possible in order to not bloat the app and keep build times fast.
- I've used the Memory Profiler to assist me in finding any egregious allocations and memory hazards.

#### Maintainability
- As mentioned above, I've used little external dependencies.
- 99% of logic is out of any view and in my ViewModels/Models which have tests written for them.
- Use of `ViewModels` retain state across configuration changes, leading to less code in the view dealing with state based code. They also morph business logic gathered from our `Models` to a more presentable format.
- Use of `Models` compartmentalise business logic.

#### Security
The app has been setup to interface with the outside world using HTTPS2 and all user stored data is encrypted. Further work can be done to add DexGuard to detect rooted devices and scramble code effectively.

#### Simplicity
The app does not use any external complex math libraries or classes that have been bodged in. The app has an architecture (MVI+CleanArch) which naturally lends itself to a Compose-based workflow.

Kotlin coroutines make it easy to perform intense work that should be kept away from the main thread. `JobExecutor` provides an easy abstraction layer in creating units of asynchronous work.

#### Scalability
The app can be geared up to use build variants. We can have a build variant for I've added a `BuildWrapper` class that can be injected into almost anywhere that is able to provide different configurations based on devices and specific API levels.

I've targeted a low `minSdk` level to ensure maximum developer convenience whilst still being able to install on many devices.

Further work can be done to use GraphQL which provides an ever-updating contract between client and server, rather than interfacing with a HTTP server.

#### Readability
It has an easy to comprehend folder structure:
- 🗂️ di - for dependency injection
- 🗂️ repository - for any network abstractions and business logic
- 🗂️ entities - for any data classes
- 🗂️ viewmodel - for viewmodels
- 🗂️ view - for any compose/activity/fragment/custom views

It also has an easy to comprehend module structure:
- libfoundation - imported into every module that provides all our common classes
- libtest - imported into every module and provides test-only dependencies
- feature`<name>` - for any feature modules
- app - main module

With the above module structure, we can use [Play On-Demand delivery](https://developer.android.com/guide/playcore/feature-delivery/on-demand) to download modules ad-hoc lowering the size of the initial download.


#### Testability
The app is very testable with business logic and view logic being tested as part of unit and UI tests. Further work can be done by improving developer experience with the use of data generator classes which can be used to mock API responses.



## Building and running
This project requires the following:
- Android Studio - Android Studio Ladybug | 2024.2.1 Patch 3
- In order to build the app, you need the Android SDK. It's usually downloaded and installed in your home folder when you install Android Studio.
- The app targets API 36 so installing an image version equivalent or close to that API would be ideal.
- JDK 8 or higher.

1. `git clone` the repo or download it as a zip.
2. Open the extracted folder in Android Studio.
3. Let Android Studio download all the dependencies.
4. Hit the green play button in the top right hand corner to run the app.

## Running tests

### Unit tests
- Tests are written in JUnit.
- Right-clicking the top-most folder this app is hosted in, click `Run All Tests`. Alternatively, I have included a run configuration that runs all tests for you which is available at `.idea/runConfigration/all_unit_tests.xml`.

### UI tests
- Please run all tests on an emulator/device with API 28 or higher. This constraint comes about by mockk, the mocking framework I'm using.
- I tested on a Pixel 9 Pro with an API of 36.
- All UI tests are located in `app/src/androidTest`, so right-clicking that folder and clicking `Run All Tests` will allow you to run those tests. Alternatively, I have included a run configuration that runs all tests for you which is available at `.idea/runConfigration/all_ui_tests.xml`.

## Things to note
- UI Tests only run on emulators/devices running API 28 or above.
- The amount field does have some quirky behaviour when typing and sometimes pushes the cursor back. Given more time I can alleviate these issues.
- I have tested many components but due to time constraints some wrapper classes I have not been able to test and I would have liked to have added more UI tests. Though the essentials are all tested `ViewModels` and `Model`.
- FxApp uses `Fragments` and is not 100% Compose. Why? Navigation in pure Compose is an arduous task especially if you have state in different screens. Navigation using the standard `Fragment` based approach, which is in a battle-tested library, is much easier (although you have to write more shell `Fragments`) and works well with different components such as the side drawer and bottom navigation bar.

## Acknowledgements

- The Frankfurter API for providing an easy-to-use API for converting currency  https://www.frankfurter.app/docs/
- Turbine for testing Flows https://github.com/cashapp/turbine
- Kaspresso for UI tests https://github.com/KasperskyLab/Kaspresso
- Koin for DI https://insert-koin.io/
- HTTP4K for a small easy to use API client https://www.http4k.org/
