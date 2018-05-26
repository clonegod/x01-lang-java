package jvm.sysproperties;

import java.util.Map.Entry;

/**
 * Java运行时相关的常用系统属性：
 * 
 * java.home	C:\Java\jdk1.7.0_40\jre
 * java.specification.version	1.7
 * java.class.version			51.0
 * sun.boot.library.path		C:\Java\jdk1.7.0_40\jre\bin
 * 
 * sun.boot.class.path	C:\Java\jdk1.7.0_40\jre\lib\resources.jar;......
 * java.ext.dirs				C:\Java\jdk1.7.0_40\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
 * java.class.path				E:\source\practice-java\jvm\target\classes;......
 * 
 * path.separator				;
 * file.encoding				UTF-8
 * file.separator				\
 * line.separator					
 * java.endorsed.dirs			C:\Java\jdk1.7.0_40\jre\lib\endorsed
 * user.dir						E:\source\practice-java\jvm
 */
public class PrintSystemProperties {
	public static void main(String[] args) {
		
		for(Entry<Object, Object> prop : System.getProperties().entrySet()) {
			System.out.println(prop.getKey() + "\t" + prop.getValue());
		}
	}
}

/**
java.runtime.name	Java(TM) SE Runtime Environment
sun.boot.library.path	C:\Java\jdk1.7.0_40\jre\bin
java.vm.version	24.0-b56
java.vm.vendor	Oracle Corporation
java.vendor.url	http://java.oracle.com/
path.separator	;
java.vm.name	Java HotSpot(TM) 64-Bit Server VM
file.encoding.pkg	sun.io
user.country	CN
user.script	
sun.java.launcher	SUN_STANDARD
sun.os.patch.level	Service Pack 1
java.vm.specification.name	Java Virtual Machine Specification
user.dir	E:\source\practice-java\jvm
java.runtime.version	1.7.0_40-b43
java.awt.graphicsenv	sun.awt.Win32GraphicsEnvironment
java.endorsed.dirs	C:\Java\jdk1.7.0_40\jre\lib\endorsed
os.arch	amd64
java.io.tmpdir	C:\Users\ADMINI~1\AppData\Local\Temp\
line.separator	

java.vm.specification.vendor	Oracle Corporation
user.variant	
os.name	Windows 7
sun.jnu.encoding	GBK
java.library.path	C:\Java\jdk1.7.0_40\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;C:/Java/jdk1.8.0_91/bin/../jre/bin/server;C:/Java/jdk1.8.0_91/bin/../jre/bin;C:/Java/jdk1.8.0_91/bin/../jre/lib/amd64;C:\Java\jdk1.8.0_91\bin;E:\local\apache-maven-3.3.9\bin;E:\local\nexus-2.12.1-01\bin;C:\Program Files (x86)\AMD APP\bin\x86_64;C:\Program Files (x86)\AMD APP\bin\x86;C:\Program Files\Broadcom\Broadcom 802.11 Network Adapter\Driver;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Program Files\Intel\WiFi\bin\;C:\Program Files\Common Files\Intel\WirelessCommon\;C:\Program Files (x86)\ATI Technologies\ATI.ACE\Core-Static;D:\install\mysql\bin;D:\Program Files\nodejs\;E:\local\mongodb-win\bin;;d:\Program Files (x86)\Microsoft VS Code\bin;C:\Users\Administrator\AppData\Roaming\npm;C:\Users\Administrator\AppData\Local\Programs\Fiddler;E:\local\eclipse;;.
java.specification.name	Java Platform API Specification
java.class.version	51.0
sun.management.compiler	HotSpot 64-Bit Tiered Compilers
os.version	6.1
user.home	C:\Users\Administrator
user.timezone	
java.awt.printerjob	sun.awt.windows.WPrinterJob
file.encoding	UTF-8
java.specification.version	1.7
java.class.path	E:\source\practice-java\jvm\target\classes;E:\repos\maven\cglib\cglib\2.2\cglib-2.2.jar;E:\repos\maven\asm\asm\3.3.1\asm-3.3.1.jar
user.name	Administrator
java.vm.specification.version	1.7
sun.java.command	jvm.classloader.PrintSystemProperties
java.home	C:\Java\jdk1.7.0_40\jre
sun.arch.data.model	64
user.language	zh
java.specification.vendor	Oracle Corporation
awt.toolkit	sun.awt.windows.WToolkit
java.vm.info	mixed mode
java.version	1.7.0_40
java.ext.dirs	C:\Java\jdk1.7.0_40\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
sun.boot.class.path	C:\Java\jdk1.7.0_40\jre\lib\resources.jar;C:\Java\jdk1.7.0_40\jre\lib\rt.jar;C:\Java\jdk1.7.0_40\jre\lib\sunrsasign.jar;C:\Java\jdk1.7.0_40\jre\lib\jsse.jar;C:\Java\jdk1.7.0_40\jre\lib\jce.jar;C:\Java\jdk1.7.0_40\jre\lib\charsets.jar;C:\Java\jdk1.7.0_40\jre\lib\jfr.jar;C:\Java\jdk1.7.0_40\jre\classes
java.vendor	Oracle Corporation
file.separator	\
java.vendor.url.bug	http://bugreport.sun.com/bugreport/
sun.io.unicode.encoding	UnicodeLittle
sun.cpu.endian	little
sun.desktop	windows
sun.cpu.isalist	amd64 
 */