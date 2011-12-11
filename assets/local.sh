#!/system/bin/sh -x

BASEDIR="/data/data/ru.meizu.m9.r00t/files"

test -x /system/xbin/su
if [ "$?" == 0 ] ; then 
    echo "already r00ted"
    exit 0
fi

echo "running from uid 0..."

echo "prepare busybox and su..."
chmod 4755 "$BASEDIR"/busybox
chmod 4755 "$BASEDIR"/su

echo "store original /system  to new path..."
"$BASEDIR"/busybox mkdir -p "$BASEDIR"/system
"$BASEDIR"/busybox mount -o bind /system "$BASEDIR"/system

echo "mount /system into memory..."
"$BASEDIR"/busybox mount -t tmpfs none /system

echo "link original /system  content to new location..."
"$BASEDIR"/busybox cp -sf "$BASEDIR"/system/* /system/

echo "installing busybox and su..."
"$BASEDIR"/busybox mkdir -p /system/xbin
"$BASEDIR"/busybox --install -s /system/xbin
"$BASEDIR"/busybox cp "$BASEDIR"/su /system/xbin/su

echo "check adfree /etc/hosts file in /data..." 
test -f /data/data/hosts
if [ "$?" == 0 ] ; then
    echo "change system /etc/hosts to adfree version..."
    "$BASEDIR"/busybox cp -sf /data/data/hosts /system/etc/
else
    echo "adfree is not installed or not configured properly."
fi

if [ "$?" == 0 ] ; then
    echo "done. now you have temporary root rights."
else
    echo "oops, something wrong."
fi
