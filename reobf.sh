#!/bin/sh -x
COMPILE_TARGET=mcpc-1.4.5-snapshot.jar
REOBF_TARGET=cpcm-1.4.5-snapshot.jar
rm -f $COMPILE_TARGET
cp lib/craftbukkit-1.4.5-R0.3-SNAPSHOT.jar $COMPILE_TARGET
pushd bin
cp ../lib/ModLoader.class .  # TODO: compile from source to avoid this hack
unzip -o ../lib/trove-3.0.3.jar gnu/ >/dev/null # TODO: find out how to add this without extracting here
cp -r ../src/META-INF .  # TODO: find out why this isn't copied from src
jar ufM ../$COMPILE_TARGET *
popd
rm -f $REOBF_TARGET
java -cp ../SrgTools/asm-all-3.3.1.jar:../SrgTools/opencsv-2.3.jar:../SrgTools/src ApplySrg --srg cpcm.srg --in $COMPILE_TARGET --inheritance $COMPILE_TARGET --out $REOBF_TARGET
cp $COMPILE_TARGET test-mcpc-server/
cp $REOBF_TARGET test-cpcm-server/
