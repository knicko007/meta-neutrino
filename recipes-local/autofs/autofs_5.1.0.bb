SUMMARY = "Kernel based automounter for linux"
SECTION = "base"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

DEPENDS += "libtirpc flex-native bison-native"

inherit autotools-brokensep systemd

SRC_URI = "${KERNELORG_MIRROR}/linux/daemons/autofs/v5/autofs-${PV}.tar.gz \
           file://autofs-5.0.7-include-linux-nfs.h-directly-in-rpc_sub.patch \
           file://no-bash.patch \
           file://cross.patch \
           file://libtirpc.patch \
           file://libtirpc-name-clash-backout.patch \
           file://autofs-5.0.7-do-not-check-for-modprobe.patch \
           file://fix_disable_ldap.patch \
           file://autofs-5.0.7-fix-lib-deps.patch \
           file://add-the-needed-stdarg.h.patch \
           file://using-pkg-config-to-detect-libxml-2.0-and-krb5.patch \
           file://force-STRIP-to-emtpy.patch \
           file://remove-bashism.patch \
"

SRC_URI[md5sum] = "b7724a9a55923f3c06933a8dfd1e79d3"
SRC_URI[sha256sum] = "794a28d178d2b21a2380ee71cbdb3b15b4be47c06f3de255b8c5c4351f046b26"

inherit update-rc.d pkgconfig

INITSCRIPT_NAME = "autofs"
INITSCRIPT_PARAMS = "defaults"

# FIXME: modules/Makefile has crappy rules that don't obey LDFLAGS
CFLAGS += "${LDFLAGS}"

PACKAGECONFIG[systemd] = "--with-systemd=${systemd_unitdir}/system,--without-systemd,systemd"

PACKAGECONFIG ?= "${@base_contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}"

EXTRA_OEMAKE = "DONTSTRIP=1"
EXTRA_OECONF += "--disable-mount-locking \
                --enable-ignore-busy --with-openldap=no \
                --with-sasl=no --with-libtirpc=yes \
                --with-path=${STAGING_BINDIR_NATIVE} \
"
CACHED_CONFIGUREVARS = "ac_cv_path_RANLIB=${RANLIB} \
                        ac_cv_path_RPCGEN=rpcgen \
"

do_configure_prepend () {
    sed -e "s:filagdir:flagdir:" -i configure.in
    if [ ! -e acinclude.m4 ]; then
        cp aclocal.m4 acinclude.m4
    fi
}

do_install_append () {
    if [ -d ${D}/run ]; then
	rmdir ${D}/run
    fi
    if [ -d ${D}${localstatedir}/run ]; then
	rmdir ${D}${localstatedir}/run
    fi
}

pkg_postinst_${PN} () {
echo "mkdir /media/nfs" >> /etc/init.d/aa_media-tmpfs.sh
}

INSANE_SKIP_${PN} = "dev-so"

RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"
SYSTEMD_SERVICE_${PN} = "autofs.service"
