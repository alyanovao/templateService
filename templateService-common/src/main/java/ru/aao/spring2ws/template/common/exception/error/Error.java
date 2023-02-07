package ru.aao.spring2ws.template.common.exception.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.jetty.server.Response;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    @ApiModelProperty(value = "Код ошибки")
    @SerializedName("code")
    @Expose
    private int code;

    @ApiModelProperty(value = "Расширенный код ошибки")
    @SerializedName("compositeCode")
    @Expose
    private String compositeCode;

    @ApiModelProperty(value = "Текст ошибки")
    @SerializedName("message")
    @Expose
    private String message;

    @ApiModelProperty(value = "Расширенный текст ошибки")
    @SerializedName("detailMessage")
    @Expose
    private String detailMessage;

    public Error(ErrorDetail err, String detailMessage) {
        this.compositeCode = null;
        switch(err) {
            case Unknown_error:
                this.code = Response.SC_BAD_REQUEST;
                this.message = "Unknown error";
                break;
            case Inner_error:
                this.code = Response.SC_INTERNAL_SERVER_ERROR;
                this.message = "Inner error";
                break;
            case External_service_error:
                this.code = Response.SC_INTERNAL_SERVER_ERROR;
                this.message = "External error";
                break;
            case Parse_error:
                this.code = Response.SC_BAD_REQUEST;
                this.message = "Parse error";
                break;
            default:
                this.code = Response.SC_BAD_REQUEST;
                this.message = "Unknown error";
                break;
        }
        this.detailMessage = detailMessage;
    }

    public String toString() {
        return "Error(code=" + this.getCode() + ", compositeСode=" + this.getCompositeCode() + ", message=" + this.getMessage() + ", detailMessage=" + this.getDetailMessage() + ")";
    }
}
