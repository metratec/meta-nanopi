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

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

SRC_URI = "git://github.com/friendlyarm/linux;protocol=http;bareclone=1;branch=${KBRANCH}"

KBUILD_DEFCONFIG_nanopi-neo2 = "sunxi_arm64_defconfig"
KBUILD_DEFCONFIG_nanopi-neo-core = "sunxi_defconfig"

# NOTE: This is what FriendlyWRT's build-kernel.sh does.
KERNEL_CONFIG_COMMAND = "oe_runmake_call -C ${S} CC="${KERNEL_CC}" O=${B} ${KBUILD_DEFCONFIG}"

# FIXME: The tweaked KERNEL_CONFIG_COMMAND breaks config fragments,
# so we patch the in-tree config instead.
#SRC_URI_append_nanopi-neo-core = " file://nanopi-neo-core.cfg"
SRC_URI_append_nanopi-neo-core = " file://nanopi-neo-core-cfg.patch"

#SRC_URI += "file://nanopi-neo2.scc \
#            file://nanopi-neo2.cfg \
#            file://nanopi-neo2-user-config.cfg \
#            file://nanopi-neo2-user-patches.scc \
#           "

KBRANCH = "sunxi-4.14.y"

LINUX_VERSION ?= "4.14.111"
LINUX_VERSION_EXTENSION ?= "-sunxi"

SRCREV="${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "nanopi-(neo2|neo-core)"
