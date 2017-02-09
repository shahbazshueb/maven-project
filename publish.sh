
#!/bin/bash

if [[ `git status --porcelain` ]]; then
  echo "clean working directory"

fi
versionText=$(cat build.sbt | grep "version := " | tr -d :)

sbt publish
git checkout mvn-repo
git add mvn-repo
git commit -m $versionText
git push origin mvn-repo
git checkout master
