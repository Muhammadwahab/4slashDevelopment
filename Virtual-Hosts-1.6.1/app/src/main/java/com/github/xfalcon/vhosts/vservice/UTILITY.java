package com.github.xfalcon.vhosts.vservice;

/**
 * Created by 4slashDevelopers on 10/2/2017.
 */

public class UTILITY {
    public static String host="#general ipv4\n" +
            "138.68.143.206\t888.com\n" +
            "138.68.143.206 \t32red.com\n" +
            "138.68.143.206 \tpokerstars.com\n" +
            "138.68.143.206 \tcasino.com\n" +
            "138.68.143.206 \t4slash.com\n" +
            "138.68.143.206 \t.youtube.com\n" +
            "138.68.143.206 \tfacebook.com\n" +
            "\n" +
            "#general ipv6\n" +
            ":: \t\t\twww.a.com\n" +
            "::1\t\t\twww.q.com\n" +
            "fe80::1 \twww.a.com\n" +
            "fd15::615e:7461:69a5:430c \tblog.a.com\n" +
            "fe89:abcd:abcd:abcd:abcd:abcd:abcd:abcd \twww.b.com\n" +
            "\n" +
            "\n" +
            "#wildcard ipv4:\n" +
            "# a.com=>192.168.100.1\n" +
            "# www.a.com=>192.168.100.1\n" +
            "# *.a.com =>192.168.100.1\n" +
            "# d.a.com=>192.168.100.2\n" +
            "\n" +
            "192.168.100.1\t\t .a.com  # use \".\" \n" +
            "192.168.100.2 \t\td.a.com # match d.a.com first\n" +
            "\n" +
            "#wildcard ipv6:\n" +
            "# a.com=>fe89:abcd:abcd:abcd:abcd:abcd:abcd:abcd\n" +
            "# www.a.com=>fe89:abcd:abcd:abcd:abcd:abcd:abcd:abcd\n" +
            "# *.a.com =>fe89:abcd:abcd:abcd:abcd:abcd:abcd:abcd\n" +
            "# d.a.com=>fe89::1\n" +
            "fe89:abcd:abcd:abcd:abcd:abcd:abcd:abcd\t\t \t.a.com  # use \".\" \n" +
            "fe89::1 \t\td.a.com\t# match d.a.com first";
}
