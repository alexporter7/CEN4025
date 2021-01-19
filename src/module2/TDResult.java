package module2;

/**
 * <h4>TD Result Class</h4>
 * Used to return ResultType Enums to handle exceptions and provide
 * a useful message for debugging.
 * <p>
 * This was created by Alex Porter and is provided AS-IS.
 *
 * @author Alex Porter
 * @since 2021-01-19
 */

public class TDResult {

    private String returnMessage;
    private ResultType resultType;

    public enum ResultType {
        PASS,
        FAIL
    }

    public TDResult(String returnMessage) {
        this.returnMessage = returnMessage;
        this.resultType = ResultType.FAIL;
    }

    public TDResult(String returnMessage, ResultType resultType) {
        this.returnMessage = returnMessage;
        this.resultType = resultType;
    }

    /**
     * Set the return message for the TDResult object.
     * @param returnMessage (String) message to return
     */
    public void setReturnMessage(String returnMessage) {
        if(returnMessage != null) {
            this.returnMessage = returnMessage;
        }
    }

    /**
     * Set the Result Type for the TDResult Object
     * @param resultType (ResultType) FAIL or PASS
     */
    public void setResultType(ResultType resultType) {
        if(resultType == ResultType.PASS || resultType == ResultType.FAIL) {
            this.resultType = resultType;
        }
    }

    /**
     * Get the message set for the Result
     * @return (String) The message currently set for the object
     */
    public String getReturnMessage() {
        return this.returnMessage;
    }

    /**
     * Get the ResultType set for the Result
     * @return (ResultType) The current ResultType set for the object
     */
    public ResultType getResultType() {
        return this.resultType;
    }

}
