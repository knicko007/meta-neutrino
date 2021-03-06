SUMMARY = "Standard Neutrino Linux system"
DESCRIPTION = "Package group bringing in packages needed for Neutrino Linux system"
PR = "r6"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-custom-needed \
"

RDEPENDS_packagegroup-custom-needed = "\
	virtual/stb-hal-libs \
	image-config \	
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	e2fsprogs-tune2fs \
	fbshot \
	minidlna \
	procps \
	resolvconf \
	udpxy \
	vsftpd \
	xupnpd \
	tzdata \
	tzdata-europe \
	udev-extraconf \
	"

RDEPENDS_packagegroup-custom-needed_append = "${@'opkg' if DISTRO != 'coolstream-hd1_flash' else ''}"
