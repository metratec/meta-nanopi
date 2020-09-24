DESCRIPTION = "U-Boot port for friendlyarm"

require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SPL_BINARY="u-boot-sunxi-with-spl.bin"

SRC_URI = "git://github.com/friendlyarm/u-boot.git;protocol=https;branch=sunxi-v2017.x"

SRC_URI += "file://boot.cmd"

# FIXME: This version of Yocto apparently does not support merging config files,
# so we patch the default config.
# NOTE: This is necessary in order to fix up the device tree.
# The device tree is not exactly the same as used in the kernel.
SRC_URI_append_nanopi-neo-core = " \
	file://defconfig-nanopi-neo-core.patch \
	file://sun8i-h3-nanopi-neo-core.dts \
"

do_device_trees() {
        cp ${WORKDIR}/*.dts ${S}/arch/arm/dts/
}
addtask device_trees after do_patch before do_configure

PE = "1"

SRCREV = "73e3bbb832d1effe90ed13e026811633a29e8557"
SRCPV = "v2017.x+git${SRCREV}"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

UBOOT_ENV_SUFFIX = "scr"
UBOOT_ENV = "boot"

do_compile_append() {
    ${B}/tools/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/${UBOOT_ENV_BINARY}
}
