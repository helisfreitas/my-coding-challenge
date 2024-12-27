package dev.helis.ownwctool.process;

public record CountResult(
        int lines,
        int words,
        int chars,
        int bytes,
        String fileName) {
   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lines >= 0) {
            sb.append(lines).append(" ");
        }
        if (words >= 0) {
            sb.append(words).append(" ");
        }
        if (chars >= 0) {
            sb.append(chars).append(" ");
        }
        if (bytes >= 0) {
            sb.append(bytes).append(" ");
        }

        sb.append(fileName);

        return sb.toString();
    }
}
