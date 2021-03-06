FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
RDEPENDS_${PN}_libc-uclibc = " ${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam-plugin-limits pam-plugin-keyinit', '', d)} libiconv gettext-runtime"

SRC_URI += "file://sudoers"
SRC_URI_append_libc-uclibc += "file://match.c_build_fix.patch"

EXTRA_OECONF_append_libc-uclibc +="--without-noexec"

do_install_append () {
	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/sudoers ${D}${sysconfdir}
}
