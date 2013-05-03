#! /bin/bash
declare -a users=()
git log --pretty=format:"%an" ./src/ | sort - | uniq | while read line
do
	i="$line";
	git log --author="$i" --pretty=tformat: --numstat -C -- ./src/ | \
	gawk -v name="$i" '{ add += $1 ; subs += $2 ; loc += $1 - $2 } END { printf "%s : \n\tadded lines: %i \n\tremoved lines: %i \n\ttotal lines: %i\n",name,add,subs,loc }' -
done
