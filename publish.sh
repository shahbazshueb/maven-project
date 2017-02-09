
#!/bin/bash

if [[ `git status --porcelain` ]]; then
  echo("clean working directory")
fi

sbt publish
git checkout mvn-repo
git add mvn-repo
git commit -m "commit message"
git push origin
git checkout master
