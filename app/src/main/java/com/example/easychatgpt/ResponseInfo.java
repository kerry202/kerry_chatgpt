package com.example.easychatgpt;

import java.io.Serializable;
import java.util.List;

public class ResponseInfo implements Serializable {
    /**
     * id : chatcmpl-6ttePVZ2G2mQeNw88ZwDXliyAJElm
     * object : chat.completion
     * created : 1678779981
     * model : gpt-3.5-turbo-0301
     * usage : {"prompt_tokens":29,"completion_tokens":309,"total_tokens":338}
     * choices : [{"message":{"role":"assistant","content":"\n\n作为一家小型银行，美国硅谷银行的破产不会对中国金融系统或经济体系产生直接影响。然而，这可能会引发市场上的一些波动和不确定性。\n\n首先，中国企业和个人可能会受到影响，如果他们在美国硅谷银行开立了账户或存款。这些人可能需要寻找新的银行来管理他们的资金，并支付一些额外费用，例如账户转移或提前取款费用。\n\n其次，美国硅谷银行主要服务于科技和初创企业，这些企业可能会受到影响。这些企业可能需要寻找新的银行来获取融资和贷款，并支付更高的利率或费用。这可能会对中国企业在美国市场上的竞争力产生一定的影响。\n\n总的来说，美国硅谷银行的破产不会对中国金融系统产生直接影响，但可能会对中国企业和个人带来一些不便。"},"finish_reason":"stop","index":0}]
     */

    public String id;
    public String object;
    public int created;
    public String model;
    public UsageBean usage;
    public List<ChoicesBean> choices;

    public static class UsageBean implements Serializable {
        /**
         * prompt_tokens : 29
         * completion_tokens : 309
         * total_tokens : 338
         */

        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }

    public static class ChoicesBean implements Serializable {
        /**
         * message : {"role":"assistant","content":"\n\n作为一家小型银行，美国硅谷银行的破产不会对中国金融系统或经济体系产生直接影响。然而，这可能会引发市场上的一些波动和不确定性。\n\n首先，中国企业和个人可能会受到影响，如果他们在美国硅谷银行开立了账户或存款。这些人可能需要寻找新的银行来管理他们的资金，并支付一些额外费用，例如账户转移或提前取款费用。\n\n其次，美国硅谷银行主要服务于科技和初创企业，这些企业可能会受到影响。这些企业可能需要寻找新的银行来获取融资和贷款，并支付更高的利率或费用。这可能会对中国企业在美国市场上的竞争力产生一定的影响。\n\n总的来说，美国硅谷银行的破产不会对中国金融系统产生直接影响，但可能会对中国企业和个人带来一些不便。"}
         * finish_reason : stop
         * index : 0
         */

        public MessageBean message;
        public String finish_reason;
        public int index;

        public static class MessageBean implements Serializable {
            /**
             * role : assistant
             * content :

             作为一家小型银行，美国硅谷银行的破产不会对中国金融系统或经济体系产生直接影响。然而，这可能会引发市场上的一些波动和不确定性。

             首先，中国企业和个人可能会受到影响，如果他们在美国硅谷银行开立了账户或存款。这些人可能需要寻找新的银行来管理他们的资金，并支付一些额外费用，例如账户转移或提前取款费用。

             其次，美国硅谷银行主要服务于科技和初创企业，这些企业可能会受到影响。这些企业可能需要寻找新的银行来获取融资和贷款，并支付更高的利率或费用。这可能会对中国企业在美国市场上的竞争力产生一定的影响。

             总的来说，美国硅谷银行的破产不会对中国金融系统产生直接影响，但可能会对中国企业和个人带来一些不便。
             */

            public String role;
            public String content;
        }
    }
}
