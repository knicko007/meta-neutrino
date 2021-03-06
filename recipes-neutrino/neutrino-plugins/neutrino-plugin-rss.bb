DESCRIPTION = "Neutrino Lua RSS Reader"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
HOMEPAGE = ""
MAINTAINER = "Jacek Jendrzej"
DEPENDS = "lua5.2 expat"
RDEPENDS_${PN} = "lua-expat lua-feedparser"

SRCREV = "0.09c"
PR = "1"


SRC_URI = "file://rss.cfg \
	   file://rss.lua \
	   file://rssreader.conf \
"

S = "${WORKDIR}/"

do_install () {
	install -d ${D}/var/tuxbox/plugins
	install -m 644 ${S}/rss.lua ${D}/var/tuxbox/plugins
	install -m 644 ${S}/rss.cfg ${D}/var/tuxbox/plugins
	if [ ${USE_VAR} == "yes" ];then
		install -d ${D}/var/tuxbox/config
		install -m 644 ${S}/rssreader.conf ${D}/var/tuxbox/config
	else
		install -d ${D}/etc/neutrino/config
		install -m 644 ${S}/rssreader.conf ${D}/etc/neutrino/config
	fi
}




