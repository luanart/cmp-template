import SwiftUI
import ComposeApp
import FirebaseCore

@main
struct iOSApp: App {
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin(app: DIContainer.koinApp)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
