# virt2vmx

Convert libvirt domain XML to VMX format. At current stage the project is meant to be a quick tool
for importing qemu-kvm VMs to VMWare infrastructure, not a comprehensive converting tool.

## Requirements

For VM disk conversion `qemu-img` command is required.

## Installation

Download from http://example.com/FIXME.

## Usage

    $ sudo java -jar virt2vmx-0.1.0-standalone.jar -f /path/to/VM.xml
    
This will write the VMX file and the VMDK to `pwd`. Note that `sudo` is usually required due to the
default permissions on libvirt domain XML files and `.qcow2` files. 

## Options

```
-f --file NAME Qemu file to convert
```

### Bugs

Probably plenty of bugs involving conversion, so please make a issue if you encounter one!

## License

Copyright © 2020 Tsuribori

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
