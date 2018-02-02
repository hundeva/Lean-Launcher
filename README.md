# Lean Launcher
by hundeva (https://github.com/hundeva/Lean-Launcher)

This launcher is based on the o-mr1 version of Amir Zaidi's Rootless Pixel Launcher: https://github.com/amirzaidi/Launcher3/tree/o-mr1?files=1

The vision is to provide something similar to Pixel Launcher, with a few useful toggles. For example, I think the bottom search bar on the home screen looks awful, and I want to get rid of it, now I can. I'm using this launcher as my daily driver, and I have yet to meet an issue, that is not present on the original Launcher3.

## Features

Everything, that is available in Rootless Pixel Launcher, with some added options, such as:

- option to hide "At A Glance"
- option to hide bottom search bar on your home screen
- option to hide app search bar at the top of your app drawer
- option to disable the spring effect when overscrolling your app drawer
- option to lock your desktop, to prevent accidental changes (and to be able to use shortcuts better)
- option to force dark or light theme, not just based on your wallpaper
- hide apps from your drawer (searching apps works for hidden apps as well)

## Package name

In order for "At A Glance" to work, the package name has to be the same as the original Pixel Launcher. If I understand it correctly, the Google app sends an explicit broadcast, which can only be received by the Pixel Launcher package. For this reason, there is no Play Store release so far.

There is a possible workaround, to create a companion app with the Pixel Launcher package name, which will receive and forward the broadcasts, but that is not planned for now, maybe if there is enough interest.

## Bug reports

Open an issue, with as much details as you can give, "the launcher crashed" is not going to cut it. Reproduction steps, adb logs, anything that you can provide helps.

## Feature requests

Open an issue, I will try to respond and see if it is something that I'm willing to do. No promises though. :)
