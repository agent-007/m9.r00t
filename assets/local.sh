#!/system/bin/sh -x

BASEDIR="/data/data/ru.meizu.m9.r00t/files"

#test -x /system/bin/su
test -x /sbin/su
if [ "$?" == 0 ] ; then 
    echo "already r00ted"
    exit 0
fi

echo "running from uid 0..."
"$BASEDIR"/busybox rm -r "$BASEDIR"/system

echo "prepare busybox and su..."
chmod 4755 "$BASEDIR"/busybox
chmod 4755 "$BASEDIR"/su

echo "remount / to rw mode..."
mount -w -o remount -t rootfs rootfs /

#echo "store original /system/bin to new path..."
#"$BASEDIR"/busybox mkdir -p "$BASEDIR"/system/bin
#"$BASEDIR"/busybox mount -o bind /system/bin "$BASEDIR"/system/bin
#
#
#echo "mount /system/bin into memory..."
#"$BASEDIR"/busybox mount -t tmpfs none /system/bin
#
#echo "link original /system/bin content to new location..."
#"$BASEDIR"/busybox ln -s "$BASEDIR"/system/bin/* /system/bin/

echo "installing busybox and su..."
chmod 755 /sbin
"$BASEDIR"/busybox ln -s "$BASEDIR"/busybox /sbin
/sbin/busybox --install -s /sbin
"$BASEDIR"/busybox cp -f "$BASEDIR"/su /sbin
    
echo "store original /system/etc to new path..."
"$BASEDIR"/busybox mkdir -p "$BASEDIR"/system/etc
"$BASEDIR"/busybox mount -o bind /system/etc "$BASEDIR"/system/etc

echo "mount /system/etc into memory..."
"$BASEDIR"/busybox mount -t tmpfs none /system/etc

echo "link original /system/etc content to new location..."
"$BASEDIR"/busybox ln -s "$BASEDIR"/system/etc/* /system/etc/

echo "check adfree files in /data..." 
test -f /data/data/hosts
if [ "$?" == 0 ] ; then
    echo "change system /etc/hosts to adfree version..."
    "$BASEDIR"/busybox cp -sf /data/data/hosts /system/etc/
else
    echo "adfree is not installed or not configured properly."
fi

echo "looking for alternate fonts..."

test -f /sdcard/fonts/DroidSansFallback.ttf
if [ "$?" == 0 ] ; then
    "$BASEDIR"/busybox rm -rf /data/local/fonts
    "$BASEDIR"/busybox mkdir -p /data/local/fonts
    "$BASEDIR"/busybox cp /sdcard/fonts/* /data/local/fonts
else
    "$BASEDIR"/busybox rm -rf /data/local/fonts
fi

test -f /data/local/fonts/DroidSansFallback.ttf
if [ "$?" == 0 ] ; then
    echo "mount /system/fonts into memory..."
    "$BASEDIR"/busybox mount -t tmpfs none /system/fonts
    "$BASEDIR"/busybox cp -sf /data/local/fonts/* /system/fonts/
    
    setprop persist.service.adb.enable 1
    killall com.android.launcher
    am start -n com.android.launcher/com.android.launcher2.Launcher
    killall com.android.systemui
    am startservice -n com.android.systemui/.statusbar.StatusBarService
    killall com.android.mms
    killall com.android.phone
else
    echo "system fonts is good for you."
fi

echo "remount / to ro mode..."
mount -r -o remount -t rootfs rootfs /

if [ "$?" == 0 ] ; then
    echo "done. now you have temporary root rights."
else
    echo "oops, something wrong."
fi
