# IEM1Chr - Advanced Android Launcher 

[![Android Architecture](https://shields.io)](#)
[![Kotlin](https://shields.io)](#)
[![License](https://shields.io)](#)
                     
                      **IEM1Chr*
is a high-performance, deeply customizable Android homescreen environment engineered for fluid navigation and zero-overhead customization. Built on a modular architecture, it bridges the gap between unrooted simplicity and low-level system personalization, featuring a standalone repository-based continuous delivery system.

---

                    ##  Core Architecture Features

* **Gesture Engine:** Real-time event handling supporting multi-touch mapping, double-taps, velocity-tracked swipes, and actionable long-press bindings.
* **Dynamic Asset Pipeline:** Decoupled icon-pack parser capable of hot-loading, caching, and mapping non-standard application vector buckets on the fly.
* **Persistent State Controller:** ACID-compliant preference serialization layer ensuring user layouts, gesture maps, and custom themes remain unbroken across update cycles.
* **Over-The-Air (OTA) Core:** Autonomous repository-polling update engine that checks, validates, and installs hotfixes without losing system state or configuration data.
* **Dual-Mode Compatibility:** Optimized execution paths that dynamically scale feature sets based on Root/Privileged or Standard API permissions.

---

                   ##  Built With

* **Language:** Kotlin (1.9+)
* **Asynchronous Execution:** Kotlin Coroutines & Flow
* **UI Engine:** Jetpack Compose / View Abstractions
* **Dependency Management:** Gradle Version Catalogs

---

            ## System Architecture & Modular Layout

The codebase follows Clean Architecture principles, decoupling core system events from UI rendering:

```text
app/
└── src/main/java/com/jeropmelanie/iem1chr/
    ├── core/          # Core loops, application lifecycle management, & hardware optimization
    ├── gestures/      # Gesture detection matrix, motion event dispatchers, & thresholds
    ├── icons/         # Asset pipeline, runtime icon masking, & bitmap caching
    ├── updates/       # Repo-polling engine, version diffing, & secure APK staging
    └── ui/            # Layout rendering & interaction components
```

---

               ##  Deployment & Initialization

To get the development environment running locally or compile production builds, refer to the step-by-step onboarding guide:

 **Read the [SETUP.md](SETUP.md) Guide**

---

                 ## Contributing

We welcome contributions from the developer community! Whether you are fixing bugs, optimizing physics algorithms, or refining the asset pipeline, your help is appreciated.

### How to Get Started
1. **Fork the Repository:** Create your own fork of the project to your GitHub account.
2. **Clone & Branch:** Clone your fork locally and create a feature branch:
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit Changes:** Keep your commits focused, descriptive, and clean:
   ```bash
   git commit -m "feat: optimize gesture velocity detection threshold"
   ```
4. **Push & Pull Request:** Push your branch to GitHub and open a Pull Request against our `main` branch.

### Code of Conduct
* Maintain clean, commented, and decoupled code following the existing MVVM architectural patterns.
* Ensure all updates pass local compilation rules without breaking backward compatibility for unrooted deployments.

---

                 ##   License

Distributed under the Apache 2.0 License. See `LICENSE` for more information.
