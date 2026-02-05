//
//  DIContainer.swift
//  iosApp
//
//  Created by Cadis on 15/12/25.
//

import ComposeApp

class DIContainer {
    static let koinApp: (Koin_coreKoinApplication) -> Void = { koinApp in
        koinApp.addAnalyticsModule(analyticsTracker: FirebaseAnalyticsImpl())
    }
}