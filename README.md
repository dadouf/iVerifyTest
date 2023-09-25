# iVerify -- Take Home: Smart Lock Device Scanner

## Instructions

### Context

> You are working for a security company called HelloWorld LLC which offers Enterprise users a
> mobile application to scan the health and security of their smart lock devices. The application
> can be distributed to users via MDM or by downloading the app from the App Store and using an
> access code that is provisioned via email. Your task is to develop a client application for the
> existing backend. The app needs to comply to security requirements set by the industry and
> customers.

### Task

> Develop a one-screen application for enterprise admins to view all devices enrolled in the
> HelloWorld enterprise product. Admins need to view user names, the last scan date, and the access
> code attached to the device. Since there are a lot of devices, add a search feature to find a
> device by name.
>
> As smart locks are protecting access to secure facilities HelloWorlds App needs to secure itself
> against attacks. So consider that so at some point, the device the app runs on could be
> compromised. Secure the network channel between the app and server to prevent data leakage on
> compromised devices.
>
> Imagine the user entered login credentials on previous steps. The server verified the credentials
> and returned token. The token is needed to authenticate the API requests. Securely store the token
> in the app to not force users to enter credentials on every launch.

## Solution

### Run

1. Checkout repo and open project in Android Studio
2. Go to Build > Select Build Variant... and select the `release` variant
3. Run

### Notes on implementation

- Tech: to retrieve and process data from the network we use Retrofit (network), Moshi (JSON parser)
  and RxJava (concurrency); the UI is built in Jetpack Compose; we use Hilt for Dependency
  Injection.
- Features implemented: list devices, fetch devices page by page, filter/search devices.
- Security measures: the token is stored in EncryptedSharedPreferences after (fake-)retrieving it
  from the API; certificate pinning is used to secure the communication with the server.
- Scrolling the list might feel slow. It is actually an artifact of running the app in debug mode.
  You must run the app in release mode to appreciate the real performance.

### Known limitations & shortcuts taken

- Security: certificate pinning is only a basic measure to secure the network communication. It
  protects against compromised servers but not against compromised devices.
- Filtering/searching is done on the main thread, ideally it should be transferred to a computation
  thread to avoid blocking the main thread. Also, in a real-world scenario, filtering/searching
  might be performed via an API call instead of computing it on device.
- Pagination is manual. A more fleshed out implementation would leverage the Pagination library to
  make this more robust and fetch new pages automatically as the user scrolls down.
- Devices are not cached to disk, they're only stored in memory and lost when the app is closed. A
  more fleshed out implementation would use a disk cache to keep devices, e.g. with Room.
- Unit tests are minimal -- only the filtering is covered. The next areas that would be important to
  test would be the view model and the repository, e.g.: verify what happens in cases of
  success/error, verify that the correct page is fetched, verify that we can retry after failure.
