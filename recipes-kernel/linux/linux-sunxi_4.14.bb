# This file was derived from the linux-yocto-custom.bb recipe in
# oe-core.
#
# linux-yocto-custom.bb:
#
#   A yocto-bsp-generated kernel recipe that uses the linux-yocto and
#   oe-core kernel classes to apply a subset of yocto kernel
#   management to git managed kernel repositories.
#
# Warning:
#
#   Building this kernel without providing a defconfig or BSP
#   configuration will result in build or boot errors. This is not a
#   bug.
#
# Notes:
#
#   patches: patches can be merged into to the source git tree itself,
#            added via the SRC_URI, or controlled via a BSP
#            configuration.
#
#   example configuration addition:
#            SRC_URI += "file://smp.cfg"
#   example patch addition:
#            SRC_URI += "file://0001-linux-version-tweak.patch
#   example feature addition:
#            SRC_URI += "file://feature.scc"
#

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "https://github.com/friendlyarm/linux;protocol=git;bareclone=1;branch=${KBRANCH}"

# FIXME: This config is also in the kernel tree and should better be copied from there.
SRC_URI += "file://defconfig"

SRC_URI += "file://nanopi-neo2.scc \
            file://nanopi-neo2.cfg \
            file://nanopi-neo2-user-config.cfg \
            file://nanopi-neo2-user-patches.scc \
           "

KBRANCH = "sunxi-4.14.y"

LINUX_VERSION ?= "4.14"
LINUX_VERSION_EXTENSION ?= "-sunxi"

SRCREV="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_nanopi-neo2 = "nanopi-neo2"
