

# OpenShift Applier for Labs Assistant

This is an OpenShift applier inventory. I'm assuming you know how to do that, else see the CI/CD repo for docs. 

TODO - support the client container.

# Usage

Install Requirements if not already installed.

`[.openshift-applier]$ ansible-galaxy install -r requirements.yml --roles-path=roles`

Right now limited to using ansible on your localhost.

`[.openshift-applier]$ ansible-playbook apply.yml -i inventory/`

See the inventory for the filter tag options.
