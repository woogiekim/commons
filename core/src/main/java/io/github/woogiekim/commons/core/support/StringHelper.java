package io.github.woogiekim.commons.core.support;

public final class StringHelper {

    private StringHelper() {
    }

    public static String substringBySafety(
        String source,
        int beginIndex,
        int endIndex
    ) {
        var length = source.length();
        var correctedBeginIndex = beginIndex < 0 || beginIndex > length ? 0 : beginIndex;
        var correctedEndIndex = Math.min(length, endIndex);

        return source.substring(correctedBeginIndex, correctedEndIndex);
    }

    public static String lenientFormat(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"

        if (args == null) {
            args = new Object[]{"(Object[])null"};
        } else {
            for (int i = 0; i < args.length; i++) {
                args[i] = lenientToString(args[i]);
            }
        }

        // start substituting the arguments into the '%s' placeholders
        var builder = new StringBuilder(template.length() + 16 * args.length);

        var templateStart = 0;
        var index = 0;

        while (index < args.length) {
            var placeholderStart = template.indexOf("{}", templateStart);

            if (placeholderStart == -1) {
                break;
            }

            builder.append(template, templateStart, placeholderStart);
            builder.append(args[index++]);

            templateStart = placeholderStart + 2;
        }

        builder.append(template, templateStart, template.length());

        // if we run out of placeholders, append the extra args in square braces
        if (index < args.length) {
            builder.append(" [");
            builder.append(args[index++]);

            while (index < args.length) {
                builder.append(", ");
                builder.append(args[index++]);
            }

            builder.append(']');
        }

        return builder.toString();
    }

    private static String lenientToString(Object o) {
        if (o == null) {
            return "null";
        }

        try {
            return o.toString();
        } catch (Exception e) {
            var objectToString = o.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(o));

            return "<" + objectToString + " threw " + e.getClass().getName() + ">";
        }
    }
}
