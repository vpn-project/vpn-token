package com.nesterrovv.vpntoken.service;

import com.nesterrovv.vpntoken.entity.Token;
import com.nesterrovv.vpntoken.exception.ConnectionException;
import com.nesterrovv.vpntoken.repository.TokenRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository repository;


    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.repository = tokenRepository;
    }


    public Token generateToken() {
        String generatedToken = generateTokenValue();
        Token token = new Token();
        token.setToken(generatedToken);
        return save(token);
    }

    @SuppressWarnings("HiddenField")
    private String generateTokenValue() {
        try {
            String curlCommand = "curl --insecure -X POST https://super-secret-link";
            ProcessBuilder processBuilder = new ProcessBuilder(curlCommand.split(" "));
            processBuilder.redirectErrorStream(true);
            var process = processBuilder.start();
            var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String accessUrl = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("accessUrl")) {
                    Pattern pattern = Pattern.compile("\"accessUrl\":\"([^\"]*)\"");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        accessUrl = matcher.group(1);
                        break;
                    }
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                if (accessUrl != null) {
                    return accessUrl;
                } else {
                    throw new ConnectException("Access URL not found in the response.");
                }
            } else {
                throw new ConnectionException("Cannot connect to VPN server. Try later.");
            }
        } catch (IOException | InterruptedException e) {
            return Arrays.toString(e.getStackTrace());
        } catch (ConnectionException connectionException) {
            return connectionException.getMessage();
        }
    }

    public Token save(Token token) {
        return repository.save(token);
    }

    public Optional<Token> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
