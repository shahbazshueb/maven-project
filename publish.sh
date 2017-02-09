
#!/bin/bash

if [[ `git status --porcelain` ]]; then
  echo "clean working directory"
  exit 0
fi
versionText=$(cat build.sbt | grep "version := ")

sbt publish
git checkout mvn-repo
git add mvn-repo
git commit -m "$versionText"
git push origin mvn-repo
git checkout master
