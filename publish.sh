
#!/bin/bash

if [[ `git status --porcelain` ]]; then
  echo "clean working directory"
  exit 0
fi
versionText=$(cat build.sbt | grep "version := ")
if [[ `git log --all --grep="$versionText" origin/mvn-repo` ]]; then
  echo "version already published on github"
  exit 0
fi

sbt publish
git checkout mvn-repo
git add mvn-repo
git commit -m "$versionText"
git push origin mvn-repo
git checkout master
